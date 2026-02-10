print('Starting to input data in mongoDB');
db = db.getSiblingDB('opincaptransacional');

db.createCollection('plan-info');
print('Inserting in plan-info collection');
db.getCollection('plan-info').insertMany(
    [
        {
            "data": {
                "series": [
                    {
                        "planId": "64053123311631",
                        "consentId": "urn:bradescoseguros:59de136f-c00f-4b1a-b7f9-2557598a5abe",
                        "seriesId": "321",
                        "modality": "TRADICIONAL",
                        "susepProcessNumber": "15414622222222222",
                        "commercialName": "Denominação comercial do produto",
                        "serieSize": 5000000,
                        "uploadQuota": 10.11,
                        "capitalizationQuota": 10.11,
                        "raffleQuota": 10.11,
                        "gracePeriodRedemption": 48,
                        "gracePeriodForFullRedemption": 48,
                        "updateIndex": "IPCA",
                        "updateIndexOthers": "Índice de atualização Outros",
                        "readjustmentIndex": "IPCA",
                        "readjustmentIndexOthers": "Índice de reajuste Outros",
                        "bonusClause": false,
                        "frequency": "MENSAL",
                        "frequencyDescription": "string",
                        "interestRate": 10.11,
                        "broker": [
                            {
                                "susepBrokerCode": "123123123",
                                "brokerDescription": "string"
                            }
                        ],
                        "titles": [
                            {
                                "titleId": "string",
                                "registrationForm": "string",
                                "issueTitleDate": "2023-01-30",
                                "termStartDate": "2023-01-30",
                                "termEndDate": "2023-01-30",
                                "rafflePremiumAmount": {
                                    "amount": 2000.11,
                                    "currency": "BRL"
                                },
                                "contributionAmount": {
                                    "amount": 2000.11,
                                    "currency": "BRL"
                                },
                                "subscriber": [
                                    {
                                        "subscriberName": "Nome do Subscritor",
                                        "subscriberDocumentType": "OUTROS",
                                        "subscriberDocumentTypeOthers": "string",
                                        "subscriberDocumentNumber": "string",
                                        "subscriberPhones": [
                                            {
                                                "countryCallingCode": "55",
                                                "areaCode": "11",
                                                "number": "29875132"
                                            }
                                        ],
                                        "subscriberAddress": "Av Naburo Ykesaki, 1270",
                                        "subscriberAddressAdditionalInfo": "Casa B",
                                        "subscriberTownName": "Rio de Janeiro",
                                        "subscriberCountrySubDivision": "RJ",
                                        "subscriberCountryCode": "BRA",
                                        "subscriberPostCode": "17500001",
                                        "holder": [
                                            {
                                                "holderName": "Nome do Titular",
                                                "holderDocumentType": "OUTROS",
                                                "holderDocumentTypeOthers": "string",
                                                "holderDocumentNumber": "string",
                                                "holderPhones": [
                                                    {
                                                        "countryCallingCode": "55",
                                                        "areaCode": "11",
                                                        "number": "29875132"
                                                    }
                                                ],
                                                "holderAddress": "Av Naburo Ykesaki, 1270",
                                                "holderAddressAdditionalInfo": "Casa B",
                                                "holderTownName": "Rio de Janeiro",
                                                "holderCountrySubDivision": "RJ",
                                                "holderCountryCode": "BRA",
                                                "holderPostCode": "17500001",
                                                "holderRedemption": false,
                                                "holderRaffle": false
                                            }
                                        ]
                                    }
                                ],
                                "technicalProvisions": [
                                    {
                                        "pmcAmount": {
                                            "amount": 2000.11,
                                            "currency": "BRL"
                                        },
                                        "pdbAmount": {
                                            "amount": 2000.11,
                                            "currency": "BRL"
                                        },
                                        "prAmount": {
                                            "amount": 2000.11,
                                            "currency": "BRL"
                                        },
                                        "pspAmount": {
                                            "amount": 2000.11,
                                            "currency": "BRL"
                                        }
                                    }
                                ]
                            }
                        ]
                    }
                ]
            }
        },
        {
            "data": {
                "series": [
                    {
                        "planId": "64053123311631",
                        "consentId": "urn:bradescoseguros:59de136f-c00f-4b1a-b7f9-2557598a5abe",
                        "seriesId": "321",
                        "modality": "TRADICIONAL",
                        "susepProcessNumber": "15414622222222222",
                        "commercialName": "Denominação comercial do produto",
                        "serieSize": 5000000,
                        "uploadQuota": 10.11,
                        "capitalizationQuota": 10.11,
                        "raffleQuota": 10.11,
                        "gracePeriodRedemption": 48,
                        "gracePeriodForFullRedemption": 48,
                        "updateIndex": "IPCA",
                        "updateIndexOthers": "Índice de atualização Outros",
                        "readjustmentIndex": "IPCA",
                        "readjustmentIndexOthers": "Índice de reajuste Outros",
                        "bonusClause": false,
                        "frequency": "MENSAL",
                        "frequencyDescription": "string",
                        "interestRate": 10.11,
                        "broker": [
                            {
                                "susepBrokerCode": "123123123",
                                "brokerDescription": "string"
                            }
                        ],
                        "titles": [
                            {
                                "titleId": "string",
                                "registrationForm": "string",
                                "issueTitleDate": "2023-01-30",
                                "termStartDate": "2023-01-30",
                                "termEndDate": "2023-01-30",
                                "rafflePremiumAmount": {
                                    "amount": 2000.11,
                                    "currency": "BRL"
                                },
                                "contributionAmount": {
                                    "amount": 2000.11,
                                    "currency": "BRL"
                                },
                                "subscriber": [
                                    {
                                        "subscriberName": "Nome do Subscritor",
                                        "subscriberDocumentType": "OUTROS",
                                        "subscriberDocumentTypeOthers": "string",
                                        "subscriberDocumentNumber": "string",
                                        "subscriberPhones": [
                                            {
                                                "countryCallingCode": "55",
                                                "areaCode": "11",
                                                "number": "29875132"
                                            }
                                        ],
                                        "subscriberAddress": "Av Naburo Ykesaki, 1270",
                                        "subscriberAddressAdditionalInfo": "Casa B",
                                        "subscriberTownName": "Rio de Janeiro",
                                        "subscriberCountrySubDivision": "RJ",
                                        "subscriberCountryCode": "BRA",
                                        "subscriberPostCode": "17500001",
                                        "holder": [
                                            {
                                                "holderName": "Nome do Titular",
                                                "holderDocumentType": "OUTROS",
                                                "holderDocumentTypeOthers": "string",
                                                "holderDocumentNumber": "string",
                                                "holderPhones": [
                                                    {
                                                        "countryCallingCode": "55",
                                                        "areaCode": "11",
                                                        "number": "29875132"
                                                    }
                                                ],
                                                "holderAddress": "Av Naburo Ykesaki, 1270",
                                                "holderAddressAdditionalInfo": "Casa B",
                                                "holderTownName": "Rio de Janeiro",
                                                "holderCountrySubDivision": "RJ",
                                                "holderCountryCode": "BRA",
                                                "holderPostCode": "17500001",
                                                "holderRedemption": false,
                                                "holderRaffle": false
                                            }
                                        ]
                                    }
                                ],
                                "technicalProvisions": [
                                    {
                                        "pmcAmount": {
                                            "amount": 2000.11,
                                            "currency": "BRL"
                                        },
                                        "pdbAmount": {
                                            "amount": 2000.11,
                                            "currency": "BRL"
                                        },
                                        "prAmount": {
                                            "amount": 2000.11,
                                            "currency": "BRL"
                                        },
                                        "pspAmount": {
                                            "amount": 2000.11,
                                            "currency": "BRL"
                                        }
                                    }
                                ]
                            }
                        ]
                    }
                ]
            }
        }
    ]
);