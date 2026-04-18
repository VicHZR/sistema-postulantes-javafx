CREATE TABLE ubigeo (
    ubigeo CHAR(6) PRIMARY KEY,
    departamento VARCHAR(50),
    provincia VARCHAR(50),
    distrito VARCHAR(50)
);

CREATE TABLE postulante (
    id_postulante SERIAL PRIMARY KEY,
    nombre VARCHAR(60) NOT NULL,
    apellido_paterno VARCHAR(60) NOT NULL,
    apellido_materno VARCHAR(60),
    dni CHAR(8) UNIQUE NOT NULL,
    correo VARCHAR(100) UNIQUE NOT NULL,
    telefono_fijo VARCHAR(20),
    telefono_celular VARCHAR(20),
    sexo CHAR(1) NOT NULL,
    ubigeo CHAR(6),
    FOREIGN KEY (ubigeo) REFERENCES ubigeo(ubigeo)
);
---------------------------

CREATE OR REPLACE PROCEDURE sp_insert_postulante(
    p_nombre VARCHAR,
    p_apellido_paterno VARCHAR,
    p_apellido_materno VARCHAR,
    p_dni CHAR(8),
    p_correo VARCHAR,
    p_fijo VARCHAR,
    p_celular VARCHAR,
    p_sexo CHAR,
    p_ubigeo CHAR(6)
)
LANGUAGE plpgsql
AS $$
BEGIN
    INSERT INTO postulante (
        nombre, apellido_paterno, apellido_materno,
        dni, correo, telefono_fijo, telefono_celular,
        sexo, ubigeo
    )
    VALUES (
        p_nombre, p_apellido_paterno, p_apellido_materno,
        p_dni, p_correo, p_fijo, p_celular,
        p_sexo, p_ubigeo
    );
END;
$$;

---------------------
SELECT tablename FROM pg_tables WHERE schemaname = 'public';
---------------------
SELECT
    id_postulante,
    nombre,
    apellido_paterno,
    apellido_materno,
    dni,
    correo,
    sexo
FROM postulante;

---------------------------
CREATE OR REPLACE PROCEDURE sp_update_postulante_correo(
    p_id INT,
    p_correo VARCHAR
)
LANGUAGE plpgsql
AS $$
BEGIN
    UPDATE postulante
    SET correo = p_correo
    WHERE id_postulante = p_id;
END;
$$;

--------------------------------

CREATE OR REPLACE PROCEDURE sp_insert_postulante(
    p_nombre VARCHAR,
    p_apellido_paterno VARCHAR,
    p_apellido_materno VARCHAR,
    p_dni CHAR(8),
    p_correo VARCHAR,
    p_fijo VARCHAR,
    p_celular VARCHAR,
    p_sexo CHAR,
    p_ubigeo CHAR(6)
)
LANGUAGE plpgsql
AS $$
BEGIN
    INSERT INTO postulante (
        nombre, apellido_paterno, apellido_materno,
        dni, correo, telefono_fijo, telefono_celular,
        sexo, ubigeo
    )
    VALUES (
        p_nombre, p_apellido_paterno, p_apellido_materno,
        p_dni, p_correo, p_fijo, p_celular,
        p_sexo, p_ubigeo
    );
END;
$$;

CREATE OR REPLACE PROCEDURE sp_update_postulante_correo(
    p_id INT,
    p_correo VARCHAR
)
LANGUAGE plpgsql
AS $$
BEGIN
    UPDATE postulante
    SET correo = p_correo
    WHERE id_postulante = p_id;
END;
$$;
--------------------------------
CREATE TABLE ubigeo (
    ubigeo CHAR(6) PRIMARY KEY,
    distrito VARCHAR(100) NOT NULL,
    provincia VARCHAR(100) NOT NULL,
    departamento VARCHAR(100) NOT NULL,
    poblacion VARCHAR(20),
    superficie VARCHAR(20),
    latitud VARCHAR(20),
    longitud VARCHAR(20)
);
----------------------
DROP TABLE IF EXISTS ubigeo_raw;

CREATE TABLE ubigeo (
    linea TEXT
);
SELECT linea
FROM public.ubigeo
LIMIT 5;

ALTER TABLE public.ubigeo RENAME TO ubigeo_raw;



CREATE TABLE public.ubigeo (
    ubigeo CHAR(6) PRIMARY KEY,
    distrito VARCHAR(100) NOT NULL,
    provincia VARCHAR(100) NOT NULL,
    departamento VARCHAR(100) NOT NULL,
    poblacion INTEGER,
    superficie NUMERIC(10,2),
    latitud NUMERIC(9,6),
    longitud NUMERIC(9,6)
);


----------------
INSERT INTO public.ubigeo (
    ubigeo,
    distrito,
    provincia,
    departamento,
    poblacion,
    superficie,
    latitud,
    longitud
)
SELECT
    split_part(linea, ';', 1)::CHAR(6),

    replace(split_part(linea, ';', 2), '"', ''),
    replace(split_part(linea, ';', 3), '"', ''),
    replace(split_part(linea, ';', 4), '"', ''),

    NULLIF(replace(split_part(linea, ';', 5), '"', ''), '')::INTEGER,
    NULLIF(replace(split_part(linea, ';', 6), '"', ''), '')::NUMERIC,
    NULLIF(replace(split_part(linea, ';', 7), '"', ''), '')::NUMERIC,
    NULLIF(replace(split_part(linea, ';', 8), '"', ''), '')::NUMERIC

FROM public.ubigeo_raw
WHERE linea NOT ILIKE 'ubigeo%';

SELECT COUNT(*) FROM public.ubigeo;

----------------
SELECT COUNT(*)
FROM public.ubigeo
WHERE poblacion IS NULL;

--------------------

SELECT COUNT(*)
FROM ubigeo
WHERE length(ubigeo) <> 6;
-------
SELECT DISTINCT departamento
FROM ubigeo
ORDER BY departamento;
--------
SELECT
  COUNT(*) FILTER (WHERE ubigeo IS NULL)     AS  COUNT(*) FILTER (WHERE ubigeo IS NULL)     AS ubigeo_null,
  COUNT(*) FILTER (WHERE distrito IS NULL)   AS distrito_null,
  COUNT(*) FILTER (WHERE provincia IS NULL)  AS provincia_null,
  COUNT(*) FILTER (WHERE departamento IS NULL) AS departamento_null
-----
ALTER TABLE postulante
ADD COLUMN ubigeo CHAR(6);
-----
ALTER TABLE postulante
ADD CONSTRAINT fk_postulante_ubigeo
FOREIGN KEY (ubigeo)
REFERENCES ubigeo (ubigeo);
----------
SELECT DISTINCT departamento
FROM ubigeo
ORDER BY departamento;
-----------
SELECT DISTINCT provincia
FROM ubigeo
WHERE departamento = 'Lima'
ORDER BY provincia;
------------
SELECT
  ubigeo,
  distrito
FROM ubigeo
WHERE departamento = 'Lima'
  AND provincia = 'Lima'
ORDER BY distrito;
-------------
CREATE INDEX idx_ubigeo_departamento ON ubigeo(departamento);
CREATE INDEX idx_ubigeo_provincia     ON ubigeo(provincia);
CREATE INDEX idx_ubigeo_distrito      ON ubigeo(distrito);


---------------------------
CREATE OR REPLACE PROCEDURE sp_insertar_postulante(
    p_nombre VARCHAR,
    p_apellido_p VARCHAR,
    p_apellido_m VARCHAR,
    p_dni CHAR(8),
    p_correo VARCHAR,
    p_fijo VARCHAR,
    p_celular VARCHAR,
    p_sexo CHAR(1),
    p_ubigeo CHAR(6)
)
LANGUAGE plpgsql
AS $$
BEGIN
    INSERT INTO postulante (
        nombre, apellido_paterno, apellido_materno,
        dni, correo, telefono_fijo, telefono_celular,
        sexo, ubigeo
    )
    VALUES (
        p_nombre, p_apellido_p, p_apellido_m,
        p_dni, p_correo, p_fijo, p_celular,
        p_sexo, p_ubigeo
    );
END;
$$;
------------------------------
CREATE OR REPLACE PROCEDURE sp_actualizar_correo(
    p_id INT,
    p_correo VARCHAR
)
LANGUAGE plpgsql
AS $$
BEGIN
    UPDATE postulante
    SET correo = p_correo
    WHERE id_postulante = p_id;
END;
$$;

------------------------
SELECT * FROM ubigeo;

SELECT * FROM postulante;