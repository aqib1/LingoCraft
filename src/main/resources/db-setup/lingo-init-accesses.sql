-- GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO postgres;
-- GRANT SELECT ON ALL TABLES IN SCHEMA public TO postgres;

GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO lingo_owner;
GRANT SELECT ON ALL TABLES IN SCHEMA public TO lingo_read;


--
-- ALTER TABLE ownership
--

-- ALTER TABLE public.user
--      OWNER TO postgres;
--
-- ALTER TABLE public.role
--     OWNER TO postgres;

ALTER TABLE public.user
     OWNER TO lingo_owner;

ALTER TABLE public.role
    OWNER TO lingo_owner;

---
--- Usage on schema public
---

-- GRANT USAGE ON SCHEMA public TO postgres;
GRANT USAGE ON SCHEMA public TO lingo_read;

--
-- Grant select rights on all tables to lingo_read
--

-- GRANT SELECT ON TABLE public.user TO postgres;
-- GRANT SELECT ON TABLE public.role TO postgres;

GRANT SELECT ON TABLE public.user TO lingo_read;
GRANT SELECT ON TABLE public.role TO lingo_read;
