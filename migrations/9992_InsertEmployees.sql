USE `cuik`;

SET NAMES 'utf8';

INSERT INTO `Employees`
    (`id`, `created`, `updated`, `name`, `email`, `is_admin`, `password_hash`)
VALUES
    ('9a0384a7-7bc9-46e3-bf51-87ed5e58c4b0', '2026-09-19 12:49:12', '2026-11-22 00:34:50', 'Estela Mancuso', 'estela.mancuso@cuik.food', 1, '$argon2id$v=19$m=65536,t=3,p=4$ntIyCQBuANUSGcjh7hXFOw$MQ7kjx/QTiiLQRJ8oV+fdkVh7Pp41LN2CEGN4GomFUM'),
    ('c4f74992-d214-4e59-85da-24198c5b3283', '2026-09-13 19:12:18', '2026-11-08 12:13:29', 'Matías Ibarra', 'matias.ibarra@cuik.food', 1, '$argon2id$v=19$m=65536,t=3,p=4$U4GlytUQQ+Q6+ycrmcE4Eg$OhHpxXjWMzPbgn79GZgTveYMeJJK0juGdNmanPDjeHY'),
    ('a4adfe9d-2cfe-4fac-90e5-1db292c9ed15', '2026-08-01 15:20:57', '2026-12-03 07:11:41', 'Rocío Navarro', 'rocio.navarro@cuik.food', 1, '$argon2id$v=19$m=65536,t=3,p=4$SwSZ6Luzz6NoQrPjbmTSJg$SgDRy8QYgPEir6njI3lczm8yR/x3Y1vE3EqM+KoZhXY'),
    ('4033e74e-d584-4b3b-be92-c195143f710a', '2026-07-11 02:26:03', '2026-09-21 04:41:09', 'Tadeo Ponce', 'tadeo.ponce@cuik.food', 1, '$argon2id$v=19$m=65536,t=3,p=4$l2s/bFLr/v3CbGBRZIP/KQ$lfRHWet0aUtfKYQYw2YFiets/UwWdTHXyoZG4l8QEto'),
    ('620faf4e-0df9-4c87-89d1-d3589ad23a6b', '2026-12-15 18:33:50', '2026-12-16 01:53:18', 'Andrés Mancuso', 'andres.mancuso@cuik.food', 1, '$argon2id$v=19$m=65536,t=3,p=4$fO21b9ou1L1zDa3r8LuPFA$N/5fjVDLeR4/4aTmtvLOf3QRGnl79iRPf9eJiiOzJxY'),
    ('8dbd2f2f-10c3-44da-a761-9429b3e8768f', '2026-03-18 07:28:16', '2026-04-03 23:09:41', 'Ignacio Molina', 'ignacio.molina@cuik.food', 1, '$argon2id$v=19$m=65536,t=3,p=4$R91DlHKb5EmNZHEOQ1IiOA$03ZbY6nSYwWp5s9QVtA5LaBMrCxkk8RnyV4g+xOrH4w'),
    ('ca8b61e7-602c-493f-a0bb-8fa6b5811cfd', '2026-08-21 14:40:32', '2026-12-26 01:44:09', 'Guillermo Ansaldi', 'guillermo.ansaldi@cuik.food', 0, '$argon2id$v=19$m=65536,t=3,p=4$BClG4sODlO9Slx62mjJUtA$ZYnXK+THg6uzmCnctigFvVXjDlvjp+2abaaPGl6tJPI'),
    ('ee40ed5f-3794-4126-971a-c0c6d5be35ca', '2026-11-26 21:07:24', '2026-12-09 05:35:55', 'Mora Domínguez', 'mora.dominguez@cuik.food', 0, '$argon2id$v=19$m=65536,t=3,p=4$Jez8F4hkJvhYls3kPZiDng$rlFOt4cOlAHwyHkcxjb7hyiIZ2sIC1QcgtKAKwvt/tU'),
    ('27fc2786-8df6-4e7c-bb01-744e8343aba4', '2026-11-10 03:18:58', '2026-11-17 05:46:28', 'Bianca Castaño', 'bianca.castano@cuik.food', 0, '$argon2id$v=19$m=65536,t=3,p=4$dYd/vBn014v7HG/3rtujGg$Qn2UZ8V2wKTrUV6IM5+goPAM7n+aadnWiPvNCktR3Ss'),
    ('1e35f5a9-2273-4dff-9b76-8e3c255ed0b0', '2026-06-22 15:07:20', '2026-08-06 08:49:10', 'Josefina González', 'josefina.gonzalez@cuik.food', 0, '$argon2id$v=19$m=65536,t=3,p=4$ULPKE8mRlIwk7I+0DFMYTw$zrlZPgSm++5PZusQ4FKYSkJ80UHJ+1aiEOG4f0yPJtY'),
    ('bbc608b5-e432-4b5a-94f9-af6cd9145aaf', '2026-04-02 06:53:17', '2026-07-21 05:30:48', 'Rocío Cisneros', 'rocio.cisneros@cuik.food', 0, '$argon2id$v=19$m=65536,t=3,p=4$QH7EvPw61TuwDuCCRTkZag$RPjJAyvQfM3ATZxl4/4jBa2ed9eWrY9fJx2d1NLQc64'),
    ('e64e22cd-1129-4633-b455-a4ddbb15aa90', '2026-12-12 01:11:27', '2026-12-23 19:28:26', 'Carolina Musso', 'carolina.musso@cuik.food', 0, '$argon2id$v=19$m=65536,t=3,p=4$gkRDGPbQdtt+KnMi2TY3Rw$f/ufvaqkV9FAD1l8jG1Qgx0XzbTCej129tx/TRAFLbc'),
    ('2334547c-a741-41f5-ac7c-859db284813d', '2026-02-23 21:19:58', '2026-12-24 20:59:25', 'Esteban Pizarro', 'esteban.pizarro@cuik.food', 0, '$argon2id$v=19$m=65536,t=3,p=4$q6euCm6Mwty8JDpu157JvQ$lBb5u48zxr7eA2DdrZsA6l7or3J4SdnohxI+inEZAwU'),
    ('0a860207-dd40-4f4b-9440-b4f486f412e9', '2026-10-01 15:17:23', '2026-11-27 00:17:43', 'Dylan Guerrero', 'dylan.guerrero@cuik.food', 0, '$argon2id$v=19$m=65536,t=3,p=4$0vhortpXch0XIqOQOIV2jQ$dwpZno9mTz1/KPWmSkg4x0v7AOsTVGzeL6dblUZzjNQ'),
    ('5291d592-7d42-4b65-9ba1-9b97cadc6773', '2026-04-21 03:10:46', '2026-09-29 04:00:38', 'Alma Domínguez', 'alma.dominguez@cuik.food', 0, '$argon2id$v=19$m=65536,t=3,p=4$/pP6nfp5px2DYv+NwSFsYg$n6p5ZenwLTqSz46V76qvOzIObCuJp/j98lPYFNZVtSg'),
    ('c2040415-d0f9-417c-bde4-9abb2735348b', '2026-05-06 03:23:48', '2026-05-14 05:18:36', 'Ariadna Zárate', 'ariadna.zarate@cuik.food', 0, '$argon2id$v=19$m=65536,t=3,p=4$W3k7QOh3+vscmn0z73CdbQ$t2dcsIo1v9qFSxcbQuBG6t5P8iyQOXn3b6vjzlMukTM'),
    ('dac40c6d-6949-4ecf-bd84-dc2b61a5c349', '2026-12-01 13:27:36', '2026-12-18 02:21:16', 'Ulises Navarro', 'ulises.navarro@cuik.food', 0, '$argon2id$v=19$m=65536,t=3,p=4$sSaY1JA5AMPF20AK0S+LKQ$ip/gqnzDiOIDJhCIXTCgAKngl2TX5XMyHbu1F0jpP6o'),
    ('37148883-7d1e-4bec-9767-6e3506a95395', '2026-04-21 23:40:07', '2026-09-22 09:30:07', 'Cristian Arce', 'cristian.arce@cuik.food', 0, '$argon2id$v=19$m=65536,t=3,p=4$Q9Zf4cdxnOeUx3DbqbARwg$Rvo4+f5FVXjDe5dREA4g+ZbTc5rGtULIev9X/tKHkDs'),
    ('a4b9ae1c-44ef-4bf7-b31f-360e9dbb51a7', '2026-01-11 15:21:22', '2026-02-14 11:28:43', 'Paola Aizenberg', 'paola.aizenberg@cuik.food', 0, '$argon2id$v=19$m=65536,t=3,p=4$HEbuN2IMM8VkSgK1niiiGw$A7drVMmt9uPRJL3aikX6c1t8dpWDPU3GRtqiKQQ/Mqg'),
    ('79fc028b-16f5-480c-b7cf-acf9421d46d0', '2026-01-10 08:11:07', '2026-06-27 17:49:22', 'Lisandro Cattaneo', 'lisandro.cattaneo@cuik.food', 0, '$argon2id$v=19$m=65536,t=3,p=4$udku+9BWE3c3tJgPkARd9w$97tVbjTFwe+eUdUOI16TfjtRvt/2COthYJk6+CJtQOs')
;