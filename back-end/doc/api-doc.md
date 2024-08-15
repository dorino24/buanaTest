# User API Doc

## Register User

Endpoint : POST /api/register

Request Body : 

```json
{
  "email"     : "test@gmail.com",
  "password"  : "password"
  
}
```

Response Body (Success):

```json
{
  "data" : "OK"
}
```

Response Body (Failed):

```json
{
  "error" : "Error condition"
}
```

## Login User

Endpoint : POST /api/login

Request Body :

```json
{
  "email"     : "test@gmail.com",
  "password"  : "password"
}
```

Response Body (Success):

```json
{
  "data": {
    "token": "TOKEN",
    "expiredAt": 123123
  }
}
```

Response Body (Failed):

```json
{
  "error" : "Error condition"
}
```
## GET USER

Endpoint : GET /api/user

Request Header 
- X-API-TOKEN : token [Mandatory]

Response Body (Success) :

```json
{
  "data" : {
    "email" : "email",
    "name"  : "name"
  }
}
```
Response Body (Failed) :

```json
{
  "error" : "Unauthorized"
}
```

## LOGOUT USER

Endpoint : DELETE /api/logout

Request Header
- X-API-TOKEN : token [Mandatory]

Response Body (Success) :

```json
{
  "data" : "OK"
}
```
Response Body (Failed) :

```json
{
  "error" : "Unauthorized"
}
```


## Member Create

Endpoint : POST /api/member/create

Request Header
- X-API-TOKEN : token [Mandatory]

Request Body :

```json
{
  "name"      : "test@gmail.com",
  "position"  : "password",
  "report_to" : "password",
  "picture"   : "password"
}
```
file picture


Response Body (Success) :

```json
{
  "data" : "OK"
}
```
Response Body (Failed) :

```json
{
  "error" : "failed"
}
```

## Member Read
Endpoint : POST /api/member/read/{id}

Request Header
- X-API-TOKEN : token [Mandatory]

Response Body (Success) :

```json
{
  "data": {
    "nama": "namna",
    "jabatan" : "jabatan",
    "posisi" : "posisi",
    "gambar" : "gambar"
  }
}
```
Response Body (Failed) :

```json
{
  "error" : "failed"
}
```

## Member Update

Endpoint : PATCH /api/member/update/{id}

Request Header
- X-API-TOKEN : token [Mandatory]


Request Body :


{
  "name"      : "test@gmail.com",
  "position"  : "password",
  "report_to" : "password",
}
file picture


Response Body (Success) :

```json
{
  "data" : "OK"
}
```
Response Body (Failed) :

```json
{
  "error" : "failed"
}
```

## Member Delete

Endpoint : DELETE /api/member/delete/{id}

Request Header
- X-API-TOKEN : token [Mandatory]

Response Body (Success) :

```json
{
  "data" : "OK"
}
```
Response Body (Failed) :

```json
{
  "error" : "Unauthorized"
}
```

