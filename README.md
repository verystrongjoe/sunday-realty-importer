# sunday-realty-importer
import real estate dealing prices in excel to mysql


1. Download sunday-realty-importer folder and import this project in elipse.

2. Open SundayRealtyImporter.java file and put the jdbc connection information to connect mysql.

3. test.xls file is just for sample. You can replace other all excels files to analyze.

4. before you run please make a table for data to be inserted in your local mysql instance.

5. Finally, you have to put two java libraries in the lib folder to the classpath. 



# 부동산 실거래가 정보 구조

| 논리명 | 물리명 | 타입(길이) |
|----|----|-----|
|시군구	|				Sigungu	|		VARCHAR(100) |
|본번		|			MainNo		|	INT |
|부번		|			SubNo		|	INT |
|단지명	|				DanjiName	|	VARCHAR(100) |
|전용면적(m2)	|		RealSize	|	INT |
|계약일			|		ContractDate|	DATE |
|거래금액(만원)	|		ContractMoney	| INT |
|층			|			Story	|		INT |
|건축년도|				ConstructYear |  INT |
|도로명주소	|			Address|			VARCHAR(200)

# table create script (sql)
```
create table realty (
Sigungu         VARCHAR(100),
MainNo          INT,
SubNo           INT,
DanjiName       VARCHAR(100),
RealSize        INT,
ContractDate    DATE,
ContractMoney   INT,
Story           INT,
ConstructYear   INT,
Address         VARCHAR(200)
)
```
