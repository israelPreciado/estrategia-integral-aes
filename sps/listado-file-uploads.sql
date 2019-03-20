USE `estrategia_integral`;
DROP procedure IF EXISTS `listado_file_uploads`;

DELIMITER $$
USE `estrategia_integral`$$
CREATE PROCEDURE `listado_file_uploads` (_xml text)
BEGIN
	declare _cliente_id int default 0;
    declare _proyecto_id int default 0;
    declare _type varchar(20);
    
    select extractValue(_xml, '//cid[1]') into _cliente_id;
    select extractValue(_xml, '//pid[1]') into _proyecto_id;
    select extractValue(_xml, '//type[1]') into _type;
    
    if _type = 'imagen' then
		select file_id, tipo, nombre, url, date_format(fecha, '%Y-%m-%d %H:%i:%s') as fecha
		from file_uploads 
		where cliente_id=_cliente_id and proyecto_id=_proyecto_id and tipo=_type and status=1
		order by fecha desc;
	else
		select file_id, tipo, nombre, url, date_format(fecha, '%Y-%m-%d %H:%i:%s') as fecha
		from file_uploads 
		where cliente_id=_cliente_id and proyecto_id=_proyecto_id and status=1
		order by fecha desc;
	end if;
END$$

DELIMITER ;