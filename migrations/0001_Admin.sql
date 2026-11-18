USE `cuik`;

SET NAMES 'utf8';

INSERT INTO `Employees`
    (`id`, `name`, `email`, `is_admin`, `password_hash`)
VALUES
    ('00000000-0000-0000-0000-000000000000', 'admin', 'admin@cuik.food', 1, '$argon2i$v=19$m=65536,t=10,p=1$yS/ozAZroTl+zQVklkg5Hg$oNf3i23mu9kUnzVCuTnA0Gtlh1LDq4RnpDSR8a2ytvg')
;
