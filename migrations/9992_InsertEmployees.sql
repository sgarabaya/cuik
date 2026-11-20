USE `cuik`;

SET NAMES 'utf8';

INSERT INTO `Employees`
    (`id`, `created`, `updated`, `name`, `email`, `is_admin`, `password_hash`)
VALUES
   ('dac40c6d-6949-4ecf-bd84-dc2b61a5c349', '2026-12-01 13:27:36', '2026-12-18 02:21:16', 'Ulises Navarro', 'ulises.navarro@cuik.food', 0, '$argon2i$v=19$m=65536,t=10,p=1$vMZx4m9R79ZIK/G+bEEzsw$SE/XpwhQsL2HXMM6/eE1Pv0Y0/WlmDMtC0jlZ/zM998'),
   ('4033e74e-d584-4b3b-be92-c195143f710a', '2026-07-11 02:26:03', '2026-09-21 04:41:09', 'Tadeo Ponce', 'tadeo.ponce@cuik.food', 1, '$argon2i$v=19$m=65536,t=10,p=1$A93K0Fw9mSJLe7+s75xJBA$l4iet7kgkC3SEMtgo2PpQblBD2IcDm7U1Ax6NoMaam0'),
   ('a4adfe9d-2cfe-4fac-90e5-1db292c9ed15', '2026-08-01 15:20:57', '2026-12-03 07:11:41', 'Rocío Navarro', 'rocio.navarro@cuik.food', 1, '$argon2i$v=19$m=65536,t=10,p=1$RBGFMwc9+Kmmm25OF4/PGw$liLRCuarNbN9/wOpAkC6VcEpkxXBpBPt8G8DwiOgF88'),
   ('bbc608b5-e432-4b5a-94f9-af6cd9145aaf', '2026-04-02 06:53:17', '2026-07-21 05:30:48', 'Rocío Cisneros', 'rocio.cisneros@cuik.food', 0, '$argon2i$v=19$m=65536,t=10,p=1$VIyhZ5j3sUr4GO6tlYZu4g$S9tAS+nvDJMt7G4K31OxzROHdEksSshFxdC4kGJQLS4'),
   ('a4b9ae1c-44ef-4bf7-b31f-360e9dbb51a7', '2026-01-11 15:21:22', '2026-02-14 11:28:43', 'Paola Aizenberg', 'paola.aizenberg@cuik.food', 0, '$argon2i$v=19$m=65536,t=10,p=1$QLvynXRo7NdC4gT3fLAj0w$W3MpkDMVU1KOeR/mqURL6Ka+ZQgOCZ6W9EL9AGfqKXo'),
   ('ee40ed5f-3794-4126-971a-c0c6d5be35ca', '2026-11-26 21:07:24', '2026-12-09 05:35:55', 'Mora Domínguez', 'mora.dominguez@cuik.food', 0, '$argon2i$v=19$m=65536,t=10,p=1$1TA7eVbr/UOeyg4ksJnBSg$31YDPODvBTfsMtaNbbN9cE8x+gTTrcVb9N5IDys2WW4'),
   ('c4f74992-d214-4e59-85da-24198c5b3283', '2026-09-13 19:12:18', '2026-11-08 12:13:29', 'Matías Ibarra', 'matias.ibarra@cuik.food', 1, '$argon2i$v=19$m=65536,t=10,p=1$5xD9GkWtKrI1AvSikrTX9g$lceTr5+TgoiOYsQxMIkfv1RN+f6gZzxSJaw8aZuw0BM'),
   ('79fc028b-16f5-480c-b7cf-acf9421d46d0', '2026-01-10 08:11:07', '2026-06-27 17:49:22', 'Lisandro Cattaneo', 'lisandro.cattaneo@cuik.food', 0, '$argon2i$v=19$m=65536,t=10,p=1$U3Jr21D3Dc2dDRdk8UIS3A$0uPZch7AUUsMXy5dr5mdX77bXFIF37St+ef7C3shzXI'),
   ('1e35f5a9-2273-4dff-9b76-8e3c255ed0b0', '2026-06-22 15:07:20', '2026-08-06 08:49:10', 'Josefina González', 'josefina.gonzalez@cuik.food', 0, '$argon2i$v=19$m=65536,t=10,p=1$ISLG+T6BLpd3EW4FZgMJNQ$KfkUIVaUnW//YN5jR6F+OqQpzuZdvqqOpyiwe66T5A8'),
   ('8dbd2f2f-10c3-44da-a761-9429b3e8768f', '2026-03-18 07:28:16', '2026-04-03 23:09:41', 'Ignacio Molina', 'ignacio.molina@cuik.food', 1, '$argon2i$v=19$m=65536,t=10,p=1$AA/fbC5btDO2uMfOPQq4nw$rliX1QI+qHn218kU4tYWLJRVeVmRtnxcV8hQw43vFAQ'),
   ('ca8b61e7-602c-493f-a0bb-8fa6b5811cfd', '2026-08-21 14:40:32', '2026-12-26 01:44:09', 'Guillermo Ansaldi', 'guillermo.ansaldi@cuik.food', 0, '$argon2i$v=19$m=65536,t=10,p=1$SQT2t7sLHfFjEuslohwfDA$8Rx1XBdGzaSrLv3xOHP37IZFg4vnf0MZkSSfwva5lRQ'),
   ('9a0384a7-7bc9-46e3-bf51-87ed5e58c4b0', '2026-09-19 12:49:12', '2026-11-22 00:34:50', 'Estela Mancuso', 'estela.mancuso@cuik.food', 1, '$argon2i$v=19$m=65536,t=10,p=1$1PjrGLtjCZY9TZu4RsdqWw$ZCSDR7d98ArJZ+YsNdADiHwip4DVqgZRTu6Dp3QhHe8'),
   ('2334547c-a741-41f5-ac7c-859db284813d', '2026-02-23 21:19:58', '2026-12-24 20:59:25', 'Esteban Pizarro', 'esteban.pizarro@cuik.food', 0, '$argon2i$v=19$m=65536,t=10,p=1$/OlkyG2vpk3dVoSILnRQ9g$yFoMm4voUrj9363WRwHPkTNpSZ0t2FXrfaIGvknOwsE'),
   ('0a860207-dd40-4f4b-9440-b4f486f412e9', '2026-10-01 15:17:23', '2026-11-27 00:17:43', 'Dylan Guerrero', 'dylan.guerrero@cuik.food', 0, '$argon2i$v=19$m=65536,t=10,p=1$6asCX67eiX4jxB4/aFxVHw$+LKWcdG+eTdDxAj36bLbOnyYV0YKdVu1FlDZqjDqgvY'),
   ('37148883-7d1e-4bec-9767-6e3506a95395', '2026-04-21 23:40:07', '2026-09-22 09:30:07', 'Cristian Arce', 'cristian.arce@cuik.food', 0, '$argon2i$v=19$m=65536,t=10,p=1$LWk4TCrc/zrJnnUFXt+b8w$jC9WZbQEIsCmJH2urqEdua5bOt1JNyBWJXlrrgjatOQ'),
   ('e64e22cd-1129-4633-b455-a4ddbb15aa90', '2026-12-12 01:11:27', '2026-12-23 19:28:26', 'Carolina Musso', 'carolina.musso@cuik.food', 0, '$argon2i$v=19$m=65536,t=10,p=1$YjbQvOO8p1SEAUqe0wx1Zw$Ms6VkLKPBKEawerVPC9VymyvMologCnuuHxXHp4OiGE'),
   ('27fc2786-8df6-4e7c-bb01-744e8343aba4', '2026-11-10 03:18:58', '2026-11-17 05:46:28', 'Bianca Castaño', 'bianca.castano@cuik.food', 0, '$argon2i$v=19$m=65536,t=10,p=1$9aAeyFkS21s/C5gUMugn5g$AcRYSvlxZ1wLs8b2ltSQNqYLZVgVnyhv6chm+BffzpE'),
   ('c2040415-d0f9-417c-bde4-9abb2735348b', '2026-05-06 03:23:48', '2026-05-14 05:18:36', 'Ariadna Zárate', 'ariadna.zarate@cuik.food', 0, '$argon2i$v=19$m=65536,t=10,p=1$4uATy+WbxI30x0DKM4lB+g$05fD8wKRTTFXot7A8sTe9R0fwltguyUtbPhrIKDm8Fg'),
   ('620faf4e-0df9-4c87-89d1-d3589ad23a6b', '2026-12-15 18:33:50', '2026-12-16 01:53:18', 'Andrés Mancuso', 'andres.mancuso@cuik.food', 1, '$argon2i$v=19$m=65536,t=10,p=1$bv9Ii9fwcTBjPaZmNRZ2lw$qS5Q7PmmyPp2CkpgCQ7LVIvVato/iGR8Sd8v/J28yCY'),
   ('5291d592-7d42-4b65-9ba1-9b97cadc6773', '2026-04-21 03:10:46', '2026-09-29 04:00:38', 'Alma Domínguez', 'alma.dominguez@cuik.food', 0, '$argon2i$v=19$m=65536,t=10,p=1$CnWD73KrVBGsqo82bKz9pg$t1jG24c4/upgy6Wu73KqH/rcrolYt1/QycnkQ06fsgE')
;