# ShoppingListServer
ShoppingList app backend. Spring-boot + Mongodb

# REST API'S
User related api's  
post /dts/users  
{  
    "email":"manager@manager.com",  
    "role":"MANAGER",  
    "username":"Demo_User",  
    "avatar":"ooOO_()_OOoo"  
}  
get /dts/user/login/{userSpace}/{userEmail}  
put /dts/user/{userSpace}/{userEmail}  
delete all /dts/admin/users/{adminSpace}/{adminEmail}  
get all /dts/admin/users/{adminSpace}/{adminEmail}  
  
Item related api's  
post /dts/items/{managerSpace}/{managerEmail}  
{ "itemId": {  
     "space": "AA",  
     "id": "2"   
     },  
 "type":"dairy",   
 "name": "milk",   
 "active":true,   
 "createdTimeStamp":"2020-11-17T16:05:24.374+00:00",   
 "createdBy": {  
     "userId":{   
         "space":"userSpace",  
         "email":"userEmail"   
         }  
         },  
    "location":{   
        "lat":1.25,   
        "lng":1.22   
        },   
        "itemAttributes": {   
            "key1":"xxx",   
            "key2":"xxx",  
            "key3":"xxx"  
            }   
}   
get /dts/items/{userSpace}/{userEmail}/{itemSpace}/{itemId}  
put /dts/items/{managerSpace}/{managerEmail}/{itemSpace}/{itemId}  
delete all /dts/admin/items/{adminSpace}/{adminEmail}  
get all dts/items/{userSpace}/{userEmail}  
get all by name pattern /dts/items/{userSpace}/{userEmail}/search/byNamePattern/{namePattern}  
get all by type /dts/items/{userSpace}/{userEmail}/search/byType/{type}  
connect one item to other /dts/items/{managerSpace}/{managerEmail}/{itemSpace}/{itemId}/children  
get children /dts/items/{userSpace}/{userEmail}/{itemSpace}/{itemId}/children  
get parent /dts/items/{userSpace}/{userEmail}/{itemSpace}/{itemId}/parents  

Operaion related api's  
post /dts/operations  
{  
    "operationId":{  
        "space":"somethingSpace",  
        "id":"43"  
    },  
    "type": "somethingType",  
    "item":{  
        "ItemId":{  
            "space":"ItemSpace",  
            "id":"4"  
        }  
    },  
    "createdTimeStamp":"2021-11-01T12:03:54.397+0000",  
    "invokedBy":{  
            "space":"UserSpace",  
            "email":"player@player.com"  
        }  
    },  
    "operationAttributes":{  
        "key1":"something",  
        "key2":"temp"  
    }  
}  
delete all /dts/admin/operations/{adminSpace}/{adminEmail}  
get all /dts/admin/operations/{adminSpace}/{adminEmail}  
