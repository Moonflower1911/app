INSERT INTO public.nz_segment (enabled, created_at, modified_at, created_by, id, modified_by, parent_id, description, name)
VALUES (true, '2025-07-23 15:07:36.025333', '2025-07-23 15:07:36.025333', 'SYSTEM',
        'a19b91c4-3235-429d-919d-b7a7061633c9', 'SYSTEM', null, null, 'Agences');
INSERT INTO public.nz_segment (enabled, created_at, modified_at, created_by, id, modified_by, parent_id, description, name)
VALUES (true, '2025-07-23 15:07:36.051415', '2025-07-23 15:07:36.051415', 'SYSTEM',
        'e8466d07-bc6f-4243-921f-08380940b33c', 'SYSTEM', 'a19b91c4-3235-429d-919d-b7a7061633c9', null,
        'Offline');
INSERT INTO public.nz_segment (enabled, created_at, modified_at, created_by, id, modified_by, parent_id, description, name)
VALUES (true, '2025-07-23 15:07:36.051415', '2025-07-23 15:07:36.051415', 'SYSTEM',
        'e8466d07-bc6f-4243-922f-08380940b33c', 'SYSTEM', 'a19b91c4-3235-429d-919d-b7a7061633c9', null,
        'VIP');
INSERT INTO public.nz_segment (enabled, created_at, modified_at, created_by, id, modified_by, parent_id, description, name)
VALUES (true, '2025-07-23 15:07:36.054140', '2025-07-23 15:07:36.054140', 'SYSTEM',
        '8f800a1d-bb5e-4f14-9b98-b709cafbbeab', 'SYSTEM', null, null, 'B2C');
INSERT INTO public.nz_segment (enabled, created_at, modified_at, created_by, id, modified_by, parent_id, description, name)
VALUES (true, '2025-07-23 15:07:36.062172', '2025-07-23 15:07:36.062172', 'SYSTEM',
        'be320e06-2259-4f4f-8eda-093765015749', 'SYSTEM', '8f800a1d-bb5e-4f14-9b98-b709cafbbeab', null, 'Reception');
INSERT INTO public.nz_segment (enabled, created_at, modified_at, created_by, id, modified_by, parent_id, description, name)
VALUES (true, '2025-07-23 15:07:36.068411', '2025-07-23 15:07:36.068411', 'SYSTEM',
        '204c1301-5b25-400d-8785-8debabcf6f73', 'SYSTEM', '8f800a1d-bb5e-4f14-9b98-b709cafbbeab', null, 'Téléphone');
INSERT INTO public.nz_segment (enabled, created_at, modified_at, created_by, id, modified_by, parent_id, description, name)
VALUES (true, '2025-07-23 15:07:36.071211', '2025-07-23 15:07:36.071211', 'SYSTEM',
        'e0583e5d-8239-46b1-b1a1-c6068c23e0ba', 'SYSTEM', '8f800a1d-bb5e-4f14-9b98-b709cafbbeab', null, 'Site Web');
INSERT INTO public.nz_segment (enabled, created_at, modified_at, created_by, id, modified_by, parent_id, description, name)
VALUES (true, '2025-07-23 15:07:36.071211', '2025-07-23 15:07:36.071211', 'SYSTEM',
        'e0583e5d-8239-46b1-b2a1-c6068c23e0ba', 'SYSTEM', '8f800a1d-bb5e-4f14-9b98-b709cafbbeab', null, 'Email');


INSERT INTO public.nz_party (birthdate, created_at, modified_at, created_by, id, modified_by, city, country, email, firstname, lastname, mobile, name, postcode, segment_id, street1, street2, type) VALUES (null, '2025-07-24 00:59:55.447549', '2025-07-24 00:59:55.447549', '8ff90f9c-a091-451a-9703-cf5fbccdb220-Hamza HABCHI', '4374c154-27ad-4757-a16d-e6cc5cdb5db3', '8ff90f9c-a091-451a-9703-cf5fbccdb220-Hamza HABCHI', null, null, 'contact@atlas-voyage.ma', null, null, null, 'Atlas voyage', null, 'e8466d07-bc6f-4243-921f-08380940b33c', null, null, 'COMPANY');
INSERT INTO public.nz_party (birthdate, created_at, modified_at, created_by, id, modified_by, city, country, email, firstname, lastname, mobile, name, postcode, segment_id, street1, street2, type) VALUES (null, '2025-07-24 01:01:46.505763', '2025-07-24 01:01:46.505763', '8ff90f9c-a091-451a-9703-cf5fbccdb220-Hamza HABCHI', 'b1df2799-09ab-451f-b95f-8ab9677d6fce', '8ff90f9c-a091-451a-9703-cf5fbccdb220-Hamza HABCHI', null, null, 'hamzahabchi.dev@gmail.com', 'Hamza', 'HABCHI', null, null, null, null, null, null, 'GUEST');
INSERT INTO public.nz_party (birthdate, created_at, modified_at, created_by, id, modified_by, city, country, email, firstname, lastname, mobile, name, postcode, segment_id, street1, street2, type) VALUES (null, '2025-07-24 01:02:23.036672', '2025-07-24 01:02:23.036672', '8ff90f9c-a091-451a-9703-cf5fbccdb220-Hamza HABCHI', '2b1ffd13-0dd2-44fa-8551-d921b4a0b72a', '8ff90f9c-a091-451a-9703-cf5fbccdb220-Hamza HABCHI', null, null, 'samir.fousshi@gmail.com', 'Samir', 'Fousshi', null, null, null, null, null, null, 'GUEST');
