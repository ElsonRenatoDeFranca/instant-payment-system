print('Start #################################################################');
db = db.getSiblingDB('opincaptransacional');

db.createCollection('events');
print('Inserting in events collection');
db.getCollection('events').insertMany(
    [
      {
        "planId": "64053123311631",
        "consentId": "urn:bradescoseguros:59de136f-c00f-4b1a-b7f9-2557598a5abe",
        "data": [
          {
            "titleId": "string",
            "eventType": "SORTEIO",
            "event": {
              "raffle": {
                "raffleAmount": {
                  "amount": 2000.11,
                  "currency": "BRL"
                },
                "raffleDate": "2023-01-30",
                "raffleSettlementDate": "2023-01-30"
              },
              "redemption": {
                "redemptionType": "ANTECIPADO_PARCIAL",
                "redemptionAmount": {
                  "amount": 2000.11,
                  "currency": "BRL"
                },
                "redemptionBonusAmount": {
                  "amount": 2000.11,
                  "currency": "BRL"
                },
                "unreturnedAmount": {
                  "amount": 2000.11,
                  "currency": "BRL"
                },
                "redemptionRequestDate": "2023-01-30",
                "redemptionSettlementDate": "2023-01-30"
              }
            }
          }
        ]
      },
      {
        "planId": "64053123311631",
        "consentId": "urn:bradescoseguros:59de136f-c00f-4b1a-b7f9-2557598a5abe",
        "data": [
          {
            "titleId": "string",
            "eventType": "SORTEIO",
            "event": {
              "raffle": {
                "raffleAmount": {
                  "amount": 2000.11,
                  "currency": "BRL"
                },
                "raffleDate": "2023-01-30",
                "raffleSettlementDate": "2023-01-30"
              },
              "redemption": {
                "redemptionType": "ANTECIPADO_PARCIAL",
                "redemptionAmount": {
                  "amount": 2000.11,
                  "currency": "BRL"
                },
                "redemptionBonusAmount": {
                  "amount": 2000.11,
                  "currency": "BRL"
                },
                "unreturnedAmount": {
                  "amount": 2000.11,
                  "currency": "BRL"
                },
                "redemptionRequestDate": "2023-01-30",
                "redemptionSettlementDate": "2023-01-30"
              }
            }
          }
        ]
      },
      {
        "planId": "64053123311631",
        "consentId": "urn:bradescoseguros:59de136f-c00f-4b1a-b7f9-2557598a5abe",
        "data": [
          {
            "titleId": "string",
            "eventType": "SORTEIO",
            "event": {
              "raffle": {
                "raffleAmount": {
                  "amount": 2000.11,
                  "currency": "BRL"
                },
                "raffleDate": "2023-01-30",
                "raffleSettlementDate": "2023-01-30"
              },
              "redemption": {
                "redemptionType": "ANTECIPADO_PARCIAL",
                "redemptionAmount": {
                  "amount": 2000.11,
                  "currency": "BRL"
                },
                "redemptionBonusAmount": {
                  "amount": 2000.11,
                  "currency": "BRL"
                },
                "unreturnedAmount": {
                  "amount": 2000.11,
                  "currency": "BRL"
                },
                "redemptionRequestDate": "2023-01-30",
                "redemptionSettlementDate": "2023-01-30"
              }
            }
          }
        ]
      }
    ]
);