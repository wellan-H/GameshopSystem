# GameshopSystem
這是根據本人自學後編寫的電玩遊戲商城，可以分為**後端**----基於*Spring Boot*編寫----以及**前端**----目前決定是*React*來編寫，但不排除學到其他框架覺得更適合會進行更改----兩部分組合，作為一個完整的作品來驗證自己的開發實力，同時將途中實際碰到的問題作為未來工作的準備。
## 後端
也是我最主要的學習方向，使用**Java**來作為開發語言，並使用**Spring Boot**框架。
DBMS選擇為**MySQL**，開發工具使用我個人覺得最方便的**IntelliJ IDEA**。
此電商系統採用MVC架構模式，根據Controller可以分為以下幾項：
- User
- Company
- Game
- Order
---
### User
此項最為單純，就是**註冊（Registry）** 與 **登入(Sign In)**
#### API 
| 說明     | 請求方法 | 路徑            | Body參數(JSON)                         |Header參數|url參數|
| -------- | -------- | --------------- | ---------------------------- |---|---|
| 註冊帳號 | POST     | /users/register | `userRequest`:註冊資料  |||
| 登入帳號 | POST     | /users/login    | `userRequest`:登入資料 |||
｜#### 參數說明
- `userRequest`:
    - `email`:string,email格式
    - `password`:string,password格式
    密碼(password)會經過MD5計算後存放。
---
### Company
此部分為遊戲公司的CRUD操作。
#### API 
| 說明     | 請求方法 | 路徑            | Body參數(JSON)                         |Header參數|url參數|
| -------- | -------- | --------------- | ---------------------------- |---|---|
| 獲取所有公司資訊 | GET     | /company/getAll |||`category`:string,非必須<br>`search`:string,非必須<br> `orderBy`:string,預設為**公司名稱(company_name)**<br>`sort`:預設為**ASC(正序)**<br>`limit`:int,預設為**5**<br>`offset`:int,預設為**0**|
| 獲取特定公司資訊 | GET     | /company/`:companyId`    |  |||
| 新增公司資訊 | POST     | /company/add    | `companyRequest`:公司資料 |||
| 修改公司資訊 | PUT     | /company/`:companyId`    | `companyRequest`:公司資料 |||
| 刪除公司資訊 | DELETE     | /company/`:companyId`    |  |||
#### 參數說明 
- 獲取所有公司資訊所需參數:
  - *`category`*:**公司的類型**（開發商/發行商）
  - *`search`*:搜尋的**關鍵字**
  - *`orderBy`*:依照什麼為<u>排序依據</u>，預設為**公司名稱(comapny_name)**，也可能依照**修改時間(last_modified_date)**、**公司簡介(company_introduction)** 等為依據
  - *`sort`*:排序，分為**反序(DESC)**與**正序(ASC)**
  - *`limit`*:限定顯示的資料筆數，需搭配 *`offset`* 使用
  - *`offset`*:跳過幾筆資料筆數，用以搭配 *`limit`* 來實現分頁查詢功能Ｆ
    
- *`companyRequest`*:為**公司資訊**，當中包含：
    - *`companyName`*:**公司名稱**
    - *`officeWeb`*:**公司官網**
    - *`companyCategory`*:**公司的類型（開發商/發行商）**
    - *`imagePath`*:**圖片網址**
    - *`companyIntroduction`*:**公司簡介**

---
### Game
此部分為遊戲資訊的CRUD操作。
#### API 
| 說明     | 請求方法 | 路徑            | Body參數(JSON)                         |Header參數|url參數|
| -------- | -------- | --------------- | ---------------------------- |---|---|
| 獲取所有遊戲資訊 | GET     | /games |||*`search`*:string,非必須<br>*`tag`*:string,非必須<br>*`plateName`*:string,非必須<br> *`orderBy`*:string,預設為**建立時間(created_date)**<br>*`sort`*:預設為**DESC(反序)**<br>*`limit`*:int,預設為**5**<br>`offset`:int,預設為**0**|
| 獲取特定遊戲資訊 | GET     | /games/`:gameId`    |  |||
| 新增遊戲資訊 | POST     | /games/add    | *`gameAddRequest`*:公司資料 |||
| 修改遊戲資訊 | PUT     | /games/`:gameId`    | *`gameAddRequest`*:公司資料 |||
| 刪除遊戲資訊 | DELETE     | /games/`:gameId`    |  |||
#### 參數說明 
- 獲取所有遊戲資訊所需參數:
  - *`search`*:搜尋的**關鍵字**
  - *`tag`*:**遊戲的類型**，如 SLG,AVG 等等
  - *`plateName`*:**遊戲支援的平台**，如PS4,PC 等等
  - *`orderBy`*:依照什麼為<u>排序依據</u>，預設為**建立時間(created_date)**，也可能依照**修改時間(last_modified_date)**、**開發商(dev_company)** 等為依據
  - *`sort`*:排序，分為**反序(DESC)**與**正序(ASC)**
  - *`limit`*:限定顯示的資料筆數，需搭配 *`offset`* 使用
  - *`offset`*:跳過幾筆資料筆數，用以搭配 *`limit`* 來實現分頁查詢功能
    
- *`gameAddRequest`*:為公司資訊，當中包含：
    - *`gameName`*:string,**遊戲名稱**
    - *`devCompany`*:string,**開發商名稱**
    - *`pubCompany`*:string,**發行商名稱**
    - *`imagePath`*:string,**圖片網址**
    - *`intro`*:string,**遊戲簡介**
    - *`properties`*:string類型的list,**遊戲類型**（可多種）
    - *`versions`*:遊戲的**平台**與對應的**價格**與**數量**
      - *`plateName`*:string,**對應平台**
      - *`price`*:int,**遊戲價格**
      - *`quantity`*:int,**遊戲的數量**
  ---
### Order
此部分為訂單的創建、查詢操作。
由於訂單是在存在用戶的情況下才成立，因此訂單功能必須在User功能後訂定路徑。
#### API 
| 說明     | 請求方法 | 路徑            | Body參數(JSON)               |Header參數|url參數|
| -------- | -------- | --------------- | ---------------------------- |---|---|
| 新增訂單 | POST     | /users/`:userId`/orders/createOrder    | *`orderAddRequest`*:訂單資料 |||
| 查詢訂單 | GET     | /users/`:userId`/orders   |  ||*`limit`*:int,預設為**5**<br>`offset`:int,預設為**0**|
#### 參數說明 
- 查詢訂單所需參數:
  - *`limit`*:限定顯示的資料筆數，需搭配 *`offset`* 使用
  - *`offset`*:跳過幾筆資料筆數，用以搭配 *`limit`* 來實現分頁查詢功能
    
- *`orderAddRequest`*:為**訂單資訊**，當中包含：
    - *`buyItemList`*:**訂單的list(buyItem)**，包含：
      - *`gameId`*:int,**遊戲的ID**
      - *`plateId`*:int,**遊戲的平台**
      - *`quantity`*:int,**訂購的數量**
---
## 前端
To Be Continued...