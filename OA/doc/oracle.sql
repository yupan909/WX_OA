-- 创建表空间
create tablespace OA   -- temporary表示临时表空间
logging 
datafile 'D:\tablespace\oa.dbf' 
size 1G 
autoextend on next 200M maxsize unlimited;

--创建用户并指定表空间
create user oa identified by password   
default tablespace OA;   

--给用户授予权限
grant connect,dba to oa;



-----------创建表
create table ACCESS_TOKEN
(
  id           VARCHAR2(32) not null,
  cre_time     VARCHAR2(50),
  corpid       VARCHAR2(100),
  corpsecret   VARCHAR2(1000),
  access_token VARCHAR2(1000),
  up_time      VARCHAR2(50)
);

comment on table ACCESS_TOKEN
  is '微信企业号唯一标识表';
comment on column ACCESS_TOKEN.cre_time
  is '创建时间';
comment on column ACCESS_TOKEN.corpid
  is '微信公众号凭证';
comment on column ACCESS_TOKEN.corpsecret
  is '微信公众号凭证秘钥';
comment on column ACCESS_TOKEN.access_token
  is '对应的AccessToken';
comment on column ACCESS_TOKEN.up_time
  is '更新时间';
  
alter table ACCESS_TOKEN
  add constraint PK_ACCESS_TOKEN primary key (ID);



-- Create table
create table FLOW_LOG
(
  id                VARCHAR2(32) not null,
  record_id         VARCHAR2(32),
  receive_user_id   VARCHAR2(50),
  receive_user_name VARCHAR2(50),
  send_user_id      VARCHAR2(50),
  send_user_name    VARCHAR2(50),
  send_time         VARCHAR2(50),
  idea              VARCHAR2(500),
  is_back           VARCHAR2(10)
);

comment on table FLOW_LOG
  is '日志记录表';
comment on column FLOW_LOG.id
  is '主键ID';
comment on column FLOW_LOG.record_id
  is '业务表ID';
comment on column FLOW_LOG.receive_user_id
  is '接收人ID';
comment on column FLOW_LOG.receive_user_name
  is '接收人名称';
comment on column FLOW_LOG.send_user_id
  is '发送人ID';
comment on column FLOW_LOG.send_user_name
  is '发送人名称';
comment on column FLOW_LOG.send_time
  is '发送时间';
comment on column FLOW_LOG.idea
  is '意见';
comment on column FLOW_LOG.is_back
  is '退回标识';
  
alter table FLOW_LOG
  add constraint PK_FLOW_LOG primary key (ID);



-- Create table
create table FLOW_WRITE
(
  id            VARCHAR2(32) not null,
  record_id     VARCHAR2(32),
  log_id        VARCHAR2(32),
  create_time   VARCHAR2(30),
  user_id       VARCHAR2(50),
  user_name     VARCHAR2(50),
  pre_user_id   VARCHAR2(50),
  pre_user_name VARCHAR2(50)
);

comment on table FLOW_WRITE
  is '待办表';
comment on column FLOW_WRITE.id
  is '主键ID';
comment on column FLOW_WRITE.record_id
  is '业务表ID';
comment on column FLOW_WRITE.log_id
  is '日志表ID';
comment on column FLOW_WRITE.create_time
  is '创建时间';
comment on column FLOW_WRITE.user_id
  is '当前办理人id';
comment on column FLOW_WRITE.user_name
  is '当前办理人名称';
comment on column FLOW_WRITE.pre_user_id
  is '上一办理人id';
comment on column FLOW_WRITE.pre_user_name
  is '上一办理人名称';
  
alter table FLOW_WRITE
  add constraint PK_FLOW_WRITE primary key (ID);



-- Create table
create table FLOW_READ
(
  id          VARCHAR2(32) not null,
  record_id   VARCHAR2(32),
  log_id      VARCHAR2(32),
  create_time VARCHAR2(30),
  user_id     VARCHAR2(50),
  user_name   VARCHAR2(50)
);

comment on table FLOW_READ
  is '已办表';
comment on column FLOW_READ.id
  is '主键ID';
comment on column FLOW_READ.record_id
  is '业务表ID';
comment on column FLOW_READ.log_id
  is '日志表ID';
comment on column FLOW_READ.create_time
  is '创建时间';
comment on column FLOW_READ.user_id
  is '办理人id';
comment on column FLOW_READ.user_name
  is '办理人名称';
  
alter table FLOW_READ
  add constraint PK_FLOW_READ primary key (ID);



-- Create table
create table OA_MAIN
(
  id            VARCHAR2(32) not null,
  type          VARCHAR2(50),
  cre_time      VARCHAR2(50),
  cre_user_id   VARCHAR2(50),
  cre_user_name VARCHAR2(100),
  title         VARCHAR2(200),
  subflag       VARCHAR2(10),
  end_time      VARCHAR2(50),
  cre_dept_id   VARCHAR2(50),
  cre_dept_name VARCHAR2(100)
);

comment on table OA_MAIN
  is '主业务表';
comment on column OA_MAIN.id
  is '主键ID';
comment on column OA_MAIN.type
  is '业务类型';
comment on column OA_MAIN.cre_time
  is '创建时间';
comment on column OA_MAIN.cre_user_id
  is '创建人id';
comment on column OA_MAIN.cre_user_name
  is '创建人名称';
comment on column OA_MAIN.title
  is '标题';
comment on column OA_MAIN.subflag
  is '状态';
comment on column OA_MAIN.end_time
  is '完成时间';
comment on column OA_MAIN.cre_dept_id
  is '创建人部门id';
comment on column OA_MAIN.cre_dept_name
  is '创建人部门名称';
  
alter table OA_MAIN
  add constraint PK_OA_MAIN primary key (ID);



-- Create table
create table OA_APPROVE_BUY
(
  id           VARCHAR2(32) not null,
  buy_name     VARCHAR2(200),
  buy_number   VARCHAR2(10),
  buy_standard VARCHAR2(200),
  buy_price    VARCHAR2(10),
  buy_reason   VARCHAR2(1000),
  buy_dept     VARCHAR2(200)
);

comment on table OA_APPROVE_BUY
  is '采购申请单';
comment on column OA_APPROVE_BUY.id
  is '主键ID';
comment on column OA_APPROVE_BUY.buy_name
  is '采购物品';
comment on column OA_APPROVE_BUY.buy_number
  is '采购数量';
comment on column OA_APPROVE_BUY.buy_standard
  is '规格及型号';
comment on column OA_APPROVE_BUY.buy_price
  is '单价';
comment on column OA_APPROVE_BUY.buy_reason
  is '采购事由';
comment on column OA_APPROVE_BUY.buy_dept
  is '使用部门';
  
alter table OA_APPROVE_BUY
  add constraint PK_OA_APPROVE_BUY primary key (ID);



-- Create table
create table OA_APPROVE_FINANCE
(
  id              VARCHAR2(32) not null,
  finance_type    VARCHAR2(200),
  finance_date    VARCHAR2(50),
  finance_content VARCHAR2(1000),
  finance_money   VARCHAR2(10)
);

comment on table OA_APPROVE_FINANCE
  is '费用报销申请单';
comment on column OA_APPROVE_FINANCE.id
  is '主键ID';
comment on column OA_APPROVE_FINANCE.finance_type
  is '报销类目';
comment on column OA_APPROVE_FINANCE.finance_date
  is '发生日期';
comment on column OA_APPROVE_FINANCE.finance_content
  is '报销内容';
comment on column OA_APPROVE_FINANCE.finance_money
  is '报销金额';
  
alter table OA_APPROVE_FINANCE
  add constraint PK_OA_APPROVE_FINANCE primary key (ID);


-- Create table
create table OA_MONEY
(
  id          VARCHAR2(32) not null,
  money_title VARCHAR2(200),
  money_month VARCHAR2(20),
  create_time VARCHAR2(50)
);

comment on table OA_MONEY
  is '工资条表';
comment on column OA_MONEY.id
  is '主键ID';
comment on column OA_MONEY.money_title
  is '工资条名称';
comment on column OA_MONEY.money_month
  is '月份';
comment on column OA_MONEY.create_time
  is '创建时间';
  
alter table OA_MONEY
  add constraint PK_OA_MONEY primary key (ID);

  
  
  -- Create table
create table OA_MONEY_DETAIL
(
  id            VARCHAR2(32) not null,
  record_id     VARCHAR2(32),
  emp_id        VARCHAR2(50),
  emp_name      VARCHAR2(100),
  money_base    VARCHAR2(10),
  money_jx      VARCHAR2(10),
  money_jj      VARCHAR2(10),
  money_cb      VARCHAR2(10),
  money_bx      VARCHAR2(10),
  money_all     VARCHAR2(10),
  emp_dept_name VARCHAR2(100)
);

comment on table OA_MONEY_DETAIL
  is '工资条明细表';
comment on column OA_MONEY_DETAIL.id
  is '主键ID';
comment on column OA_MONEY_DETAIL.record_id
  is '工资条ID';
comment on column OA_MONEY_DETAIL.emp_id
  is '员工账号';
comment on column OA_MONEY_DETAIL.emp_name
  is '员工姓名';
comment on column OA_MONEY_DETAIL.money_base
  is '基本工资';
comment on column OA_MONEY_DETAIL.money_jx
  is '绩效';
comment on column OA_MONEY_DETAIL.money_jj
  is '奖金';
comment on column OA_MONEY_DETAIL.money_cb
  is '餐补';
comment on column OA_MONEY_DETAIL.money_bx
  is '五险一金';
comment on column OA_MONEY_DETAIL.money_all
  is '合计';
comment on column OA_MONEY_DETAIL.emp_dept_name
  is '员工部门名称';
  
alter table OA_MONEY_DETAIL
  add constraint PK_OA_MONEY_DETAIL primary key (ID);
  
  
  
  -- Create table
create table OA_NOTICE
(
  id             VARCHAR2(32) not null,
  notice_title   VARCHAR2(200),
  notice_time    VARCHAR2(50),
  notice_content VARCHAR2(4000)
);

comment on table OA_NOTICE
  is '通知公告表';
comment on column OA_NOTICE.id
  is '主键ID';
comment on column OA_NOTICE.notice_title
  is '公告标题';
comment on column OA_NOTICE.notice_time
  is '发布时间';
comment on column OA_NOTICE.notice_content
  is '公告内容';
  
alter table OA_NOTICE
  add constraint PK_OA_NOTICE primary key (ID);

  
  
-- Create table
create table OA_REPORT
(
  id             VARCHAR2(32) not null,
  report_type    VARCHAR2(10),
  report_title   VARCHAR2(200),
  report_sum     VARCHAR2(1000),
  report_plan    VARCHAR2(1000),
  report_realize VARCHAR2(1000)
);

comment on table OA_REPORT
  is '工作报告表';
comment on column OA_REPORT.id
  is '主键ID';
comment on column OA_REPORT.report_type
  is '报告类型';
comment on column OA_REPORT.report_title
  is '报告标题';
comment on column OA_REPORT.report_sum
  is '工作总结';
comment on column OA_REPORT.report_plan
  is '工作计划';
comment on column OA_REPORT.report_realize
  is '工作体会';
  
alter table OA_REPORT
  add constraint PK_OA_REPORT primary key (ID);

  
  
  -- Create table
create table OA_VACATION
(
  id                  VARCHAR2(32) not null,
  vacation_type       VARCHAR2(10),
  vacation_start_date VARCHAR2(50),
  vacation_end_date   VARCHAR2(50),
  vacation_remark     VARCHAR2(1000)
);

comment on table OA_VACATION
  is '请假申请单';
comment on column OA_VACATION.id
  is '主键ID';
comment on column OA_VACATION.vacation_type
  is '请假类型';
comment on column OA_VACATION.vacation_start_date
  is '开始时间';
comment on column OA_VACATION.vacation_end_date
  is '结束时间';
comment on column OA_VACATION.vacation_remark
  is '备注';
  
alter table OA_VACATION
  add constraint PK_OA_VACATION primary key (ID);
  