/**
 *报名存储过程 测试版
 */
create procedure `addSign`(
in studentId varchar(32),
in courseId varchar(32),
in classHourId varchar(300),
out insertMessage varchar(200)
)
begin
	declare allowCourseNum int default 0;/*每个学生允许最大报的课程数*/
    declare scUUID varchar(32) default 'abc';
    declare termId varchar(32);/*学期id*/
	declare regCourseNum int default 0;/**本课程本节报名数*/
	declare courseCount int default 0;/**这个课程必须报的节数*/
	declare courseStudentNum int default 0;/**本课程允许报名多少学生**/
	declare regStuCourseNum int default 0;/*此学生总共报的课程数*/
	declare location int default 1;/*字符位置*/
	declare courseHour varchar(32);/*得到的字符*/
	declare scount int;/*此课程是否报名*/
	declare weekIndex int;/*星期几*/
	declare startTime time;/*开始上课时间*/
	declare endTime time;/*下课时间*/
	declare classHourNum int;/*此课程的课时数*/
	declare classStudentNum int;/*此课程每节课允许多少学生报名*/
	
	START TRANSACTION ;/*开始事务*/
		set insertMessage='';
		select t.classhourRequire into courseCount  from t_elec_course t where t.id=courseId;/**这个课程必须报的节数**/
		select t.maxStudentNum into courseStudentNum  from t_elec_course t where t.id=courseId;/**本课程允许报名多少学生**/
		select t.term_id into termId  from t_elec_course t where t.id=courseId;/**获得学期id**/
		select t.classhourNum into classHourNum from t_elec_course t where t.id=courseId;/*获得此课程的课时数*/
		select t.max_course into allowCourseNum  from t_elec_term t where  t.id=termId;/*此学期允许学生报的课程最大数*/
		set classStudentNum=((courseStudentNum*courseCount)/classHourNum)-0.5;/*每节课允许最大报名数,因为0.5进1所以-0.5*/
		
		if (ISNULL(classHourId)<1 && LENGTH(classHourId)>0)  /*判断报名时间是否为空,为空进行抢课,不为空抢时间*/
		then
		
				while (location<LENGTH(classHourId)) do/*循环校验学生抢课时间是否和自己报过的课时有冲突*/
					set courseHour=substring(classHourId,location,32);/*以分隔符, 获得字符*/
					set location=location+33;/*每次下标加33  以为id为32位 又加了个,号*/
					select t.weekIndex into weekIndex from t_elec_classhour t where t.id=courseHour;
					select t.week_startTime into startTime from t_elec_classhour t where t.id=courseHour;
					select t.week_endTime into endTime from t_elec_classhour t where t.id=courseHour;
					
					select count(t.id) into regCourseNum from t_elec_sign t 
					where t.stu_id=studentId and t.classhour_id is not null and t.course_id <> courseId  and t.course_id in 
					(
						select c.course_id from t_elec_classhour c where c.weekIndex=weekIndex and 
							(
							c.week_startTime between  startTime and endTime 
							or 
							c.week_endTime between  startTime and endTime
							)
					);/*获得次学生前时间段内是否报的有课程*/
					
					if(regCourseNum>0)/*判断此时间是否报名过*/
					then
						set insertMessage='对不起你的报课时有冲突!';/*自己已经报过课程时间冲突的课时*/
						set location=LENGTH(classHourId);/*结束掉循环*/
					end  if;
					
					select count(t.id) into scount from t_elec_sign t where t.course_id=courseId and t.classhour_id like concat('%',courseHour,'%');
					if(scount>=classStudentNum)/*判断此时间是否报名过*/
					then
						set insertMessage='对不起你的报课时已经报满了!';/*自己已经报过的课时是否报满*/
						set location=LENGTH(classHourId);/*结束掉循环*/
					end  if;
				end  while; 
				
				if(LENGTH(insertMessage)<=0)
				then
					
					 select count(t.id) into scount from t_elec_sign t where t.course_id=courseId and t.stu_id=studentId;
					 if(scount>0)
					 then
					 	update t_elec_sign t set t.classhour_id=classHourId   where t.course_id=courseId and t.stu_id=studentId; 
					 	if (row_count()>0) 
						then
							set insertMessage='报名成功!';/*抢课成功!*/
						else
							set insertMessage='报名失败!';/*抢课失败！*/
						end if;
					 else
					 	set insertMessage='对不起,你还没有抢到课程';
					 end if;
					 
			   end  if;
			   
		else
			select count(t.id) into regStuCourseNum from t_elec_sign t where  t.stu_id=studentId;
			if(regStuCourseNum>=allowCourseNum && allowCourseNum<>0)
			then
				set	insertMessage='对不起,你报的课程数达到最大数!';
			else	
			    
				select count(t.id) into regCourseNum from t_elec_sign t where t.course_id=courseId and t.stu_id=studentId;
				if(regCourseNum<=0)/*本课程此学生是否抢过课*/
				then
				    select count(t.id) into regCourseNum from t_elec_sign t where t.course_id=courseId;
					if(courseStudentNum<=regCourseNum)
					then
					 set	insertMessage='对不起,本课程已经报满!';
					else
						insert into t_elec_sign(id,course_id,stu_id,create_date) values(REPLACE(uuid(), '-', ''),courseId,studentId,now());
						if (row_count()>0) 
						then
							set insertMessage='抢课成功!';/*抢课成功!*/
						else
							set insertMessage='抢课失败!';/*抢课失败！*/
						end if;
					end  if;
				else
					set insertMessage='此课程你已经抢过了!';/*抢课成功!*/
			   	end if;
			   	
			 end if; 
			 
		end  if;
	commit;
end