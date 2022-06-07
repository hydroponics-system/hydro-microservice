DELETE FROM systems;

INSERT INTO systems(id, uuid, part_number, name, insert_user_id)
VALUES
(1, '71d9ec65-265b-3388-a6e4-654128db3263', '335155D000001', 'partNumberTest', 1),
(2, '3d6b0a4d-d2f3-31bf-a637-2326625bb5b0', '276674D000002', 'otherSystem', 1),
(3, 'b59892f8-9d4f-32e6-8af3-c1a5c301e04c', '449296D000003', 'testSystem', 2);
