INSERT INTO `roles` VALUES (1,'ROLE_ADMIN'),(2,'ROLE_DOCTOR'),(3,'ROLE_PATIENT');

INSERT INTO `users` (id,fullname, email, confirmation_token, enabled, profile_img, username, password) VALUES 
(1,'ADMIN','admin@gmail.com','f9027735-6fd9-4426-ba57-0a5d6dls7772',1,'/img/avatar.png','admin','$2a$10$Th3jovQba84Dul0dnXFxiOBTBKSx4s26sXi4k9s.NUge62i8rur92'),
(2,'BS CKII Hà Ngọc Hùng','tpgiahuy5@gmail.com','f9027735-6fd9-4426-ba57-0a5d64jdsa772',0,'/img/avatar.png','doctor','$2a$10$Th3jovQba84Dul0dnXFxiOBTBKSx4s26sXi4k9s.NUge62i8rur92'),
(3,'Tống Phước Gia Huy','tpgiahuy5@gmail.com','f9027735-6fd9-4426-ba57-0a5d646f7smd2',0,'/img/avatar.png','huyhue','$2a$10$Th3jovQba84Dul0dnXFxiOBTBKSx4s26sXi4k9s.NUge62i8rur92'),
(4,'PGS.TS.Phạm Thị Bích Đào','doctor1@gmail.com','f9027735-6fd9-4426-ba57-0a5skd46f7772',0,'/img/avatar.png','doctor1','$2a$10$Th3jovQba84Dul0dnXFxiOBTBKSx4s26sXi4k9s.NUge62i8rur92'),
(5,'PGS.TS.Đinh Ngọc Sơn','doctor2@gmail.com','f9027735-6fd9-4426-ba57-0a5d646fns',0,'/img/avatar.png','doctor2','$2a$10$Th3jovQba84Dul0dnXFxiOBTBKSx4s26sXi4k9s.NUge62i8rur92'),
(6,'TS.Nguyễn Hoàng Long','doctor3@gmail.com','f9027735-6fd9-4426-ba57-0a5d5s2f7772',0,'/img/avatar.png','doctor3','$2a$10$Th3jovQba84Dul0dnXFxiOBTBKSx4s26sXi4k9s.NUge62i8rur92'),
(7,'Ths.BS.Trần Đắc Đại','doctor4@gmail.com','f9027735-6fd9-4426-ba57-0a5d646kdo72',0,'/img/avatar.png','doctor4','$2a$10$Th3jovQba84Dul0dnXFxiOBTBKSx4s26sXi4k9s.NUge62i8rur92'),
(8,'BS.Nguyễn Thị Ngọc Lan','doctor5@gmail.com','f9027735-6fd9-4426-ba57-0a5d646kdi72',0,'/img/avatar.png','doctor5','$2a$10$Th3jovQba84Dul0dnXFxiOBTBKSx4s26sXi4k9s.NUge62i8rur92'),
(9,'BS CKII. Nguyễn Trọng Nghĩa','doctor6@gmail.com','f9027735-6fd9-4426-ba57-0a5d646f7dj2',0,'/img/avatar.png','doctor6','$2a$10$Th3jovQba84Dul0dnXFxiOBTBKSx4s26sXi4k9s.NUge62i8rur92'),
(10,'TS.Lê Phong','doctor7@gmail.com','f9027735-6fd9-4426-ba57-0a5d646f5272',0,'/img/avatar.png','doctor7','$2a$10$Th3jovQba84Dul0dnXFxiOBTBKSx4s26sXi4k9s.NUge62i8rur92')
;

INSERT INTO `users_roles` VALUES (1,1),(2,2),(3,3),(4,2),(5,2),(6,2),(7,2),(8,2),(9,2),(10,2);
INSERT INTO `clinics` VALUES
('1', 'Quận Ngũ Hành Sơn', NULL, 'Phòng khám Tâm Đức', NULL, NULL),
('2', 'Quận Ngũ Hành Sơn', NULL, 'Phòng khám Thiện Tâm', NULL, NULL),
('3', 'Quận Hải Châu', NULL, 'Phòng khám Đa Khoa ', NULL, NULL),
('4', 'Quận Sơn Trà', NULL, 'Phòng khám Ngũ Hành Sơn', NULL, NULL),
('5', 'Quận Sơn Trà', NULL, 'Phòng khám An Nghi', NULL, NULL);

INSERT INTO `specialties` (`id`, `name`) VALUES 
('1', 'Khoa Chấn Thương Chỉnh Hình'),
(2,'Khoa Tâm Lý Trị Liệu'),
(3,'Khoa Nhi'),
(4,'Khoa Răng Hàm Mặt'),
(5,'Khoa Sản');

INSERT INTO `doctors` (description, start_practice_date, id_doctor, id_clinic, id_specialty) VALUES
('Tận tình giúp đỡ bệnh nhân', NULL, '2', '1', '1'),
('Hỗ trợ nhiệt tình', NULL, '4', '1', '2'),
('Chăm sóc chu đáo', NULL, '5', '2', '5'),
('Kinh nghiệm lâu năm', NULL, '6', '2', '3'),
('Hỗ trợ nhiệt tình', NULL, '7', '1', '2'),
('Tiền không phải là tất cả', NULL, '8', '3', '5'),
('Sức khỏe người bệnh là trên hết', NULL, '9', '4', '3'),
('Lương y như từ mẫu', NULL, '10', '3', '1');

INSERT INTO `patients`  VALUES
('61 Lê Minh, Huế', '3', NULL);

INSERT INTO `packages` (id,duration,editable, description, name, price) VALUES
('1','60','1','Chăm sóc tùy nhu cầu bệnh nhân','Gói cơ bản', '500'),
('2','60','1', 'Chăm sóc toàn diện sức khỏe bệnh nhân','Gói toàn diện', '1000'),
('3', '60', '1', 'Chăm sóc trọn gói trong vòng 6 tháng', 'Gói 6 tháng', '3200');

INSERT INTO `packages_doctors` VALUES
 (1,2),(2,2);
 
 INSERT INTO `working_plans` VALUES 
  (2,
 '{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}',
 '{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}',
 '{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}',
 '{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}',
 '{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}',
 '{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}',
 '{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}'
 ),
  (4,
 '{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}',
 '{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}',
 '{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}',
 '{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}',
 '{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}',
 '{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}',
 '{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}'
 ),
  (5,
 '{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}',
 '{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}',
 '{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}',
 '{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}',
 '{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}',
 '{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}',
 '{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}'
 ),
  (6,
 '{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}',
 '{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}',
 '{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}',
 '{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}',
 '{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}',
 '{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}',
 '{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}'
 ),
  (7,
 '{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}',
 '{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}',
 '{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}',
 '{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}',
 '{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}',
 '{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}',
 '{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}'
 ),
  (8,
 '{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}',
 '{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}',
 '{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}',
 '{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}',
 '{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}',
 '{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}',
 '{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}'
 ),
  (9,
 '{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}',
 '{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}',
 '{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}',
 '{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}',
 '{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}',
 '{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}',
 '{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}'
 ),
  (10,
 '{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}',
 '{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}',
 '{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}',
 '{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}',
 '{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}',
 '{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}',
 '{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}'
 )
 ;

 