CREATE DATABASE IF NOT EXISTS ssmbookappoint;

USE ssmbookappoint;

CREATE TABLE book(
    book_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '图书ID',
    name varchar(100) NOT NULL  COMMENT '图书名称',
    introd varchar(1000) NOT NULL COMMENT '简介',
    number int(11) NOT NULL COMMENT '馆藏数量',
    PRIMARY KEY (book_id)
) ENGINE=InnoDb AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='图书表';

-- 初始化图书数据
INSERT INTO book (book_id, name, introd, number)
VALUES
    (1000, 'Java程序设计', 'Java语言是一门严谨啰嗦的程序语言', 10),
    (1001, '数据结构', '数据结构是计算机存储, 组织数据的方式', 10),
    (1002, '设计模式', '设计模式是一套被反复使用,多数人直销的,代码设计经验的总结', 10),
    (1003, '编译原理', '编译原理旨在介绍编译程序构造的一般原理和方法', 10),
    (1004, '大学语文', '基于长期的教学实践和学科思考', 10),
    (1005, '计算方法', '又名数值分析,为各种数学问题提供最有效的算法', 10),
    (1006, '高等数学', '涉及集合,代数等高深学科', 10);

-- 创建预约图书表
CREATE TABLE appointment(
    book_id bigint(20) NOT NULL COMMENT '图书ID',
    student_id bigint(20) NOT NULL COMMENT '学号',
    appoint_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '预约时间',
    PRIMARY KEY (book_id, student_id),
    INDEX idx_appoint_time (appoint_time)
) ENGINE=InnoDb DEFAULT CHARSET=utf8 COMMENT='预约图书列表';   

-- 创建学生数据表
CREATE TABLE student(
    student_id bigint(20) NOt NULL COMMENT '学生ID',
    password bigint(20) NOT NULL COMMENT '密码',
    PRIMARY KEY(student_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='学生统计表';

-- 初始化学生数据
INSERT INTO student (student_id, password)
VALUES
    (3211200801,346543),
	(3211200802,754323),
	(3211200803,674554),
	(3211200804,542344),
	(3211200805,298383),
	(3211200806,873973),
	(3211200807,193737),
	(3211200808,873092);
