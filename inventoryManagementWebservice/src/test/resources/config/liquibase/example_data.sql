INSERT into category
values (1, 'Testcategory', 'TEST');

INSERT into type
values (1, 'Testtype', 1);

INSERT into location
values (1, 'Testlocation');

INSERT into supplier
values (1, 'Testsupplier', 'https://www.testsupplier.net');

INSERT into department
values (1, 'Testdepartment');

INSERT into printer
values (1, 'TEST-1', 'QL-820NWB', 'tcp://192.168.0.5', '17x54');

INSERT into department_member
values (1, 1, 1, true, 1);

INSERT into inventory_item(id, item_internal_number, type_id, item_name, serial_number, location_id, supplier_id,
                           pieces,
                           pieces_stored, pieces_issued, pieces_dropped, issued_to, delivery_date, issue_date,
                           dropping_date,
                           dropping_reason, comments, status, active, department_id, old_item_number)
values (1, 'TEST-2022-0001', 1, 'test', 'ABC123', 1, 1, 11, 11, 0, 0, '', null, null, null, '', '', 'LAGERND', true,
        1, '');

INSERT into change_history
values (1, 1, '2022-08-18 10:17:26', 'Inventargegenstand angelegt.', 'Test test test', 1);