CREATE TABLE public.book_info
(
    isbn character varying(15) COLLATE pg_catalog."default" NOT NULL,
    book_name text COLLATE pg_catalog."default" NOT NULL,
    book_price double precision NOT NULL,
    CONSTRAINT book_info_pkey PRIMARY KEY (isbn)
);

CREATE TABLE public.user_info
(
    user_id bigint NOT NULL,
    user_name character varying(20) COLLATE pg_catalog."default" NOT NULL,
    user_gender character varying(4) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT user_info_pkey PRIMARY KEY (user_id)
);
