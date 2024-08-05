
--新增table
CREATE TABLE currency ( id SERIAL PRIMARY KEY, code VARCHAR(3) UNIQUE NOT NULL, name  VARCHAR(50) NOT NULL );
--新增單筆資料
INSERT INTO currency (code, name) VALUES ('USD', ‘美元’);
--查詢單筆資料by ID
SELECT * FROM currency WHERE id = 1;
--更新單筆資料by ID
UPDATE currency SET name = ‘美元’ WHERE id = 1;
--刪除單筆資料by ID
DELETE FROM currency WHERE id = 1;