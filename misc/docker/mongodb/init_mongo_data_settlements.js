print('Start #################################################################');
db = db.getSiblingDB('opincaptransacional');

db.createCollection('settlements');
print('Inserting in settlements collection');
db.getCollection('settlements').insertMany(
    [
        {
            "data": [
                {
                    "settlementId": "112233",
                    "settlementFinancialAmount": {
                        "amount": 2000.11,
                        "currency": "BRL"
                    },
                    "settlementPaymentDate": "2023-01-30",
                    "settlementDueDate": "2023-01-30",
                    "planId": "64053123311631",
                    "consentId": "urn:bradescoseguros:59de136f-c00f-4b1a-b7f9-2557598a5abe"
                }
            ]
        },
        {
            "data": [
                {
                    "settlementId": "112233",
                    "settlementFinancialAmount": {
                        "amount": 2000.11,
                        "currency": "BRL"
                    },
                    "settlementPaymentDate": "2023-01-30",
                    "settlementDueDate": "2023-01-30",
                    "planId": "64053123311631",
                    "consentId": "urn:bradescoseguros:59de136f-c00f-4b1a-b7f9-2557598a5abe"
                }
            ]
        },
        {
            "data": [
                {
                    "settlementId": "112233",
                    "settlementFinancialAmount": {
                        "amount": 2000.11,
                        "currency": "BRL"
                    },
                    "settlementPaymentDate": "2023-01-30",
                    "settlementDueDate": "2023-01-30",
                    "planId": "64053123311631",
                    "consentId": "urn:bradescoseguros:59de136f-c00f-4b1a-b7f9-2557598a5abe"
                }
            ]
        }
    ]
);
