# Rest API for VTJ name data
Shows data about names registered in Finland

Data is extracted from [https://www.avoindata.fi/data/fi/dataset/none](https://www.avoindata.fi/data/fi/dataset/none)

## API

**Forenames**
---
Returns VTJ forename data in json format.
[https://namedata-api.herokuapp.com/forenames](https://namedata-api.herokuapp.com/forenames)

* **URL**

    /forenames[?sortBy=:sortBy]
    
* **Method:**

    `GET`
    
* **Query params**

    `sortBy=[string]`
    
* **Success response**

  * **Code:** 200 </br>
  * **Content:** `[{"name":"Juhani","total":289326,"femaleTotal":0,"femaleFirstName":0,"femaleOtherNames":0,"maleTotal":289326,"maleFirstName":8311,"maleOtherNames":281015}, ...]`

