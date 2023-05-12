alter table inventory_item
    drop column dropping_queue;
alter table inventory_item
    add column dropping_queue varchar(100);
alter table inventory_item
    add column dropping_queue_pieces int;
alter table inventory_item
    add column dropping_queue_reason varchar(1275);
alter table inventory_item
    add column dropping_queue_requester int;
alter table inventory_item
    add column dropping_queue_date datetime;




