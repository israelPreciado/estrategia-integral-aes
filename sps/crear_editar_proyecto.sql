USE `estrategia_integral`;
DROP procedure IF EXISTS `crear_editar_proyecto`;

DELIMITER $$
USE `estrategia_integral`$$
CREATE PROCEDURE `crear_editar_proyecto` (_xml text)
BEGIN
	declare _proyecto_id int default 0;
	declare _cliente_id int default 0;
    declare _nombre varchar(250);    
    
    select extractValue(_xml, '//pid[1]') into _proyecto_id;
    select extractValue(_xml, '//cid[1]') into _cliente_id;
    select extractValue(_xml, '//nombre[1]') into _nombre;    
    
    if _proyecto_id = 0 then
		insert into proyectos(cliente_id, nombre, status)
        values(_cliente_id, _nombre, 1);
        set _proyecto_id = last_insert_id();		
    else
		update proyectos
        set nombre=_nombre
        where proyecto_id=_proyecto_id;		
    end if;
    
    select 'OK' as respuesta, _proyecto_id as proyecto_id;
END$$

DELIMITER ;