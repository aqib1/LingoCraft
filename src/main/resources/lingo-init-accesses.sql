GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO postgres;
GRANT SELECT ON ALL TABLES IN SCHEMA public TO postgres;

--
-- ALTER TABLE ownership
--

ALTER TABLE public.user
     OWNER TO postgres;


---
--- Usage on schema public
---

GRANT USAGE ON SCHEMA public TO postgres;

--
-- Grant select rights on all tables to lingo_read
--

GRANT SELECT ON TABLE public.user TO postgres;
