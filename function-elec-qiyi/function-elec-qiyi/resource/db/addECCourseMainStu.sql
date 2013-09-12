begin
 declare tempRemaingNum int default -1;
declare tempSelectedNum int default -1;
declare tempMyMaxNum int default -1;
declare tempManageMaxNum int default -1;
declare tempUUID varchar(32) default 'abc';
declare tempDefault int default 0;
declare tempStr varchar(32);
declare tempThisCount int  default 0;

declare tempCount int default 0;
declare tempCountLabel2 int default 0;
declare tempCountLabel3 int default 0;
declare tempCountCur int default 0;
declare tempCountSel int default 0;
declare tempInt int default -1;
declare tempExit int default -1;
declare tempCurWeek int ;
declare tempCurBeginTime datetime ;
declare tempCurEndTime dateTime ;
declare tempSelWeek int ;
declare tempSelBeginTime datetime ;
declare tempSelEndTime datetime ;
DECLARE stopFlag INT DEFAULT 0;



declare cur CURSOR for select MAINID from t_ec_course_mainstu where STUID=tstuId ;                           /**已选课程id*/
declare curCouTime CURSOR for select EWEEKNAME,BEGINTIME,ENDTIME from t_ec_course_time as t where t.FK_MAIN_ID=tmainId ;    /**当前课程时间*/        
declare curCouTimeD CURSOR for select EWEEKNAME,BEGINTIME,ENDTIME from t_ec_course_time  as t,t_ec_course_mainstu as cm where t.FK_MAIN_ID= cm.MAINID AND cm.STUID=tstuId ;   /**已选课程时间*/        
DECLARE CONTINUE  HANDLER FOR NOT FOUND set stopFlag=1; /*异常*/

START TRANSACTION ;

select remaingNum,selectednum into tempRemaingNum,tempSelectedNum from t_ec_course_main where PK_T_EC_COURSE_MAIN_ID=tmainId for update;
if (addOrDel = 1) then 

 /**select EWEEKNAME,BEGINTIME,ENDTIME into tempCurWeek,tempCurBeginTime, tempCurEndTime from t_ec_course_time where FK_MAIN_ID=tmainId ;  当前课程时间*/
select count(*)  into tempMyMaxNum from t_ec_course_mainstu where STUID = tstuId ;
select MAXCous into tempManageMaxNum from t_ec_course_manage where PK_T_EC_COURSE_MANAGE_ID=tschoolyear;
select count(*) into tempCountSel from t_ec_course_time as t,t_ec_course_mainstu as cm where t.FK_MAIN_ID= cm.MAINID AND cm.STUID=tstuId ; /**已选课程的时间数*/        
select count(*) into tempCountCur from t_ec_course_time  as t where t.FK_MAIN_ID=tmainId ; /**当前课程的时间数*/   


if (tempRemaingNum > 0 && tempSelectedNum>=0)  then
	
     if (tempMyMaxNum = tempManageMaxNum ) then
               set checkStatus = -3; /*个人最大选课数已满*/
     elseif (tempMyMaxNum = 0) then 
              /**一门课程未选**/
             set tempUUID =  REPLACE(uuid(), '-', '');
             insert into t_ec_course_mainstu(mainid,stuid,stuno,identify,schoolyear,enable,pk_t_ec_course_mainstu_id,createtime) values (tmainId,tstuId,tstuNo,tidentify,tschoolyear,tenable,tempUUID,now());
             update t_ec_course_main set remaingNum=tempRemaingNum-1,selectednum=tempSelectedNum+1 where PK_T_EC_COURSE_MAIN_ID=tmainId; 
             set checkStatus = row_count();
    else  
	   open cur;
               label:loop
                     fetch cur into tempStr; 
                 while (stopFlag = 0) do 
                     if(tempStr = tmainId) then
                         set tempInt = 1;
 			leave label;
                     else
                	set   tempCount =    tempCount + 1;
                	if (tempCount = tempManageMaxNum ) then 
				 set tempInt = 0;
			       leave label;
			end if;
                     end if;  		
                 end while;               
               end loop label; 
              close cur;
             if (tempInt = 0) then 
				set stopFlag = 0;	
                              		open curCouTimeD;
                                     		 label2:loop
                                           		fetch curCouTimeD into tempSelWeek,tempSelBeginTime,tempSelEndTime;
								while (stopFlag = 0) do
									set   tempCountLabel2 =    tempCountLabel2 + 1;								
									if (tempCountSel >= tempCountLabel2) then 	
                                                				open curCouTime;
											set   tempCountLabel3 = 0;
											label3: loop
							   					fetch curCouTime into tempCurWeek,tempCurBeginTime,tempCurEndTime;
													while (stopFlag = 0) do
														set   tempCountLabel3 =    tempCountLabel3 + 1;
														if (tempCountCur >= tempCountLabel3) then
		                                           								if (tempSelWeek =tempCurWeek &&  ((tempCurBeginTime <= tempSelBeginTime && tempCurEndTime >= tempSelBeginTime )  ||  (tempCurBeginTime <= tempSelEndTime && tempCurEndTime >= tempSelEndTime ) || (tempCurBeginTime >= tempSelBeginTime && tempCurEndTime <=tempSelEndTime))) 
                		                             								then 
				select tempSelWeek, tempCurWeek;
                                			                   							set tempInt = 1;
																set checkStatus = -2;	
																leave label3;	
                                           										end if; 
														else 
															leave label3;	
														end if;	
													end while;
						       					 end loop  label3;
										close curCouTime;  																															
									else 
										leave label2;
									end if;
								end while;
                                      		end loop  label2;
                              		close curCouTimeD;  


			 if(tempInt =0) then
                 		 set tempUUID =  REPLACE(uuid(), '-', '');
            	 		 insert into t_ec_course_mainstu(mainid,stuid,stuno,identify,schoolyear,enable,pk_t_ec_course_mainstu_id,createtime) values (tmainId,tstuId,tstuNo,tidentify,tschoolyear,tenable,tempUUID,now());
             	 		update t_ec_course_main set remaingNum=tempRemaingNum-1,selectednum=tempSelectedNum+1 where PK_T_EC_COURSE_MAIN_ID=tmainId; 
                		 set checkStatus = row_count(); 
           		 end if;	
	         
            end if;
        
     end if; 
else 
	set checkStatus = -1;  /**所选课程已报满*/
end if;
else 
/**	 select count(*)  into tempThisCount from t_ec_course_mainstu where STUID = tstuId AND MAINID=tmainId;**/
select remaingNum into tempThisCount from t_ec_course_main where PK_T_EC_COURSE_MAIN_ID=tmainId for update;
 	if (tempThisCount >= 0) then
 		 delete from  t_ec_course_mainstu where pk_t_ec_course_mainstu_id=tmainstuId ;
 		 update t_ec_course_main set remaingNum=tempRemaingNum+1,selectednum=tempSelectedNum-1 where PK_T_EC_COURSE_MAIN_ID=tmainId; 
 		 set checkStatus = row_count(); 
	else 
		 set checkStatus = 0; 
	end if;
end if;
 commit;

end