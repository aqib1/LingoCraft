CREATE TABLE public.user
(
    id uuid DEFAULT gen_random_uuid() PRIMARY KEY,
    name character varying(255) NOT NULL,
    password varchar(255)       NOT NULL,
    email varchar(320)          NOT NULL,
    phone varchar(15)           NOT NULL,
    roleId integer              NOT NULL
);