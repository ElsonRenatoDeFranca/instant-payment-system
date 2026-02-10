print('Start #################################################################');
db = db.getSiblingDB('opincaptransacional');

db.createCollection('plans');
print('Inserting in plans collection');
db.getCollection('plans').insertMany(
    [
        {
            "data": [
                {
                    "consentId": "urn:bradescoseguros:59de136f-c00f-4b1a-b7f9-2557598a5abe",
                    "brand": {
                        "name": "EMPRESA A Seguros 1",
                        "companies": [
                            {
                                "companyName": "Nome da sociedade 1",
                                "cnpjNumber": "12345678901234",
                                "products": [
                                    {
                                        "productName": "Produto Exemplo 1",
                                        "planId": "64053123311631"
                                    }
                                ]
                            }
                        ]
                    }
                }
            ]
        },
        {
            "data": [
                {
                    "consentId": "urn:bradescoseguros:59de136f-c00f-4b1a-b7f9-2557598a5abe",
                    "brand": {
                        "name": "EMPRESA A Seguros 2",
                        "companies": [
                            {
                                "companyName": "Nome da sociedade 2",
                                "cnpjNumber": "12345678901234",
                                "products": [
                                    {
                                        "productName": "Produto Exemplo 2",
                                        "planId": "64053123311631"
                                    }
                                ]
                            }
                        ]
                    }
                }
            ]
        },
        {
            "data": [
                {
                    "consentId": "urn:bradescoseguros:59de136f-c00f-4b1a-b7f9-2557598a5abe",
                    "brand": {
                        "name": "EMPRESA A Seguros 3",
                        "companies": [
                            {
                                "companyName": "Nome da sociedade 3",
                                "cnpjNumber": "12345678901234",
                                "products": [
                                    {
                                        "productName": "Produto Exemplo 3",
                                        "planId": "64053123311631"
                                    }
                                ]
                            }
                        ]
                    }
                }
            ]
        }
    ]
);