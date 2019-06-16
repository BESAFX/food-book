GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;

DROP SCHEMA public CASCADE;
CREATE SCHEMA public;

SELECT setval('' ATTACH_SEQUENCE '', (SELECT MAX(id)
                                  FROM attach) + 1);
SELECT setval('' ATTACH_TYPE_SEQUENCE '', (SELECT MAX(id)
                                           FROM attach_type) + 1);
SELECT setval('' BANK_SEQUENCE '', (SELECT MAX(id)
                                    FROM bank) + 1);
SELECT setval('' BANK_TRANSACTION_SEQUENCE '', (SELECT MAX(id)
                                                FROM bank_transaction) + 1);
SELECT setval('' BILL_PURCHASE_SEQUENCE '', (SELECT MAX(id)
                                             FROM bill_purchase) + 1);
SELECT setval('' BILL_PURCHASE_ATTACH_SEQUENCE '', (SELECT MAX(id)
                                                    FROM bill_purchase_attach) + 1);
SELECT setval('' BILL_PURCHASE_PRODUCT_SEQUENCE '', (SELECT MAX(id)
                                                     FROM bill_purchase_product) + 1);
SELECT setval('' BILL_SELL_SEQUENCE '', (SELECT MAX(id)
                                         FROM bill_sell) + 1);
SELECT setval('' BILL_SELL_ATTACH_SEQUENCE '', (SELECT MAX(id)
                                                FROM bill_sell_attach) + 1);
SELECT setval('' BILL_SELL_PRODUCT_SEQUENCE '', (SELECT MAX(id)
                                                 FROM bill_sell_product) + 1);
SELECT setval('' COMPANY_SEQUENCE '', (SELECT MAX(id)
                                       FROM company) + 1);
SELECT setval('' CONTACT_SEQUENCE '', (SELECT MAX(id)
                                       FROM contact) + 1);
SELECT setval('' CUSTOMER_SEQUENCE '', (SELECT MAX(id)
                                        FROM customer) + 1);
SELECT setval('' CUSTOMER_CONTACT_SEQUENCE '', (SELECT MAX(id)
                                                FROM customer_contact) + 1);
SELECT setval('' HISTORY_SEQUENCE '', (SELECT MAX(id)
                                       FROM history) + 1);
SELECT setval('' OFFER_SEQUENCE '', (SELECT MAX(id)
                                     FROM offer) + 1);
SELECT setval('' OFFER_ATTACH_SEQUENCE '', (SELECT MAX(id)
                                            FROM offer_attach) + 1);
SELECT setval('' OFFER_PRODUCT_SEQUENCE '', (SELECT MAX(id)
                                             FROM offer_product) + 1);
SELECT setval('' OFFER_VERIFICATION_TOKEN_SEQUENCE '', (SELECT MAX(id)
                                                        FROM offer_verification_token) + 1);
SELECT setval('' ORDER_PURCHASE_SEQUENCE '', (SELECT MAX(id)
                                              FROM order_purchase) + 1);
SELECT setval('' ORDER_PURCHASE_PRODUCT_SEQUENCE '', (SELECT MAX(id)
                                                      FROM order_purchase_product) + 1);
SELECT setval('' ORDER_PURCHASE_VERIFICATION_TOKEN_SEQUENCE '', (SELECT MAX(id)
                                                                 FROM order_purchase_verification_token) + 1);
SELECT setval('' ORDER_SELL_SEQUENCE '', (SELECT MAX(id)
                                          FROM order_sell) + 1);
SELECT setval('' ORDER_SELL_PRODUCT_SEQUENCE '', (SELECT MAX(id)
                                                  FROM order_sell_product) + 1);
SELECT setval('' ORDER_SELL_VERIFICATION_TOKEN_SEQUENCE '', (SELECT MAX(id)
                                                             FROM order_sell_verification_token) + 1);
SELECT setval('' PERSON_SEQUENCE '', (SELECT MAX(id)
                                      FROM person) + 1);
SELECT setval('' PRODUCT_SEQUENCE '', (SELECT MAX(id)
                                       FROM product) + 1);
SELECT setval('' SUPPLIER_SEQUENCE '', (SELECT MAX(id)
                                        FROM supplier) + 1);
SELECT setval('' SUPPLIER_CONTACT_SEQUENCE '', (SELECT MAX(id)
                                                FROM supplier_contact) + 1);
SELECT setval('' SUPPLIER_PAYMENT_SEQUENCE '', (SELECT MAX(id)
                                                FROM supplier_payment) + 1);
SELECT setval('' TEAM_SEQUENCE '', (SELECT MAX(id)
                                    FROM team) + 1);
SELECT setval('' TRANSACTION_TYPE_SEQUENCE '', (SELECT MAX(id)
                                                FROM transaction_type) + 1);
