INSERT INTO `roles` VALUES (1,'ROLE_ADMIN'),(2,'ROLE_DOCTOR'),(3,'ROLE_PATIENT');

INSERT INTO `users` (id,fullname, email, enabled, username, password) VALUES 
(1,'ADMIN','admin@gmail.com',1,'admin','$2a$10$Th3jovQba84Dul0dnXFxiOBTBKSx4s26sXi4k9s.NUge62i8rur92'),
(2,'BS CKII Hà Ngọc Hùng','doctor@gmail.com',1,'doctor','$2a$10$Th3jovQba84Dul0dnXFxiOBTBKSx4s26sXi4k9s.NUge62i8rur92'),
(3,'Tống Phước Gia Huy','huyhue@gmail.com',1,'huyhue','$2a$10$Th3jovQba84Dul0dnXFxiOBTBKSx4s26sXi4k9s.NUge62i8rur92'),
(4,'PGS.TS.Phạm Thị Bích Đào','doctor1@gmail.com',1,'doctor1','$2a$10$Th3jovQba84Dul0dnXFxiOBTBKSx4s26sXi4k9s.NUge62i8rur92'),
(5,'PGS.TS.Đinh Ngọc Sơn','doctor2@gmail.com',1,'doctor2','$2a$10$Th3jovQba84Dul0dnXFxiOBTBKSx4s26sXi4k9s.NUge62i8rur92'),
(6,'TS.Nguyễn Hoàng Long','doctor3@gmail.com',1,'doctor3','$2a$10$Th3jovQba84Dul0dnXFxiOBTBKSx4s26sXi4k9s.NUge62i8rur92'),
(7,'Ths.BS.Trần Đắc Đại','doctor4@gmail.com',1,'doctor4','$2a$10$Th3jovQba84Dul0dnXFxiOBTBKSx4s26sXi4k9s.NUge62i8rur92');

INSERT INTO `users_roles` VALUES (1,1),(2,2),(3,3),(4,2),(5,2),(6,2),(7,2);

INSERT INTO `clinics` VALUES
('1', NULL, NULL, 'Phòng khám Tâm Đức', NULL, NULL),
('2', NULL, NULL, 'Phòng khám Thiện Tâm', NULL, NULL),
('3', NULL, NULL, 'Phòng khám Đa Khoa ', NULL, NULL),
('4', NULL, NULL, 'Phòng khám Ngũ Hành Sơn', NULL, NULL),
('5', NULL, NULL, 'Phòng khám An Nghi', NULL, NULL);

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
('Lương y như từ mẫu', NULL, '7', '3', '2');

INSERT INTO `patients` (id_patient, address) VALUES
('3', 'hue vn');
