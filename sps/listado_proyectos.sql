USE `estrategia_integral`;
DROP procedure IF EXISTS `listado_proyectos`;

DELIMITER $$
USE `estrategia_integral`$$
CREATE PROCEDURE `listado_proyectos` (_xml text)
BEGIN
	declare _usuario_id int default 0;   
	declare _cliente_id int default 0;            
	declare _proyecto_id int default 0;   
    declare _admin int default 0;
    
    select extractValue(_xml, '//uid[1]') into _usuario_id;
    select extractValue(_xml, '//cid[1]') into _cliente_id;
    select extractValue(_xml, '//pid[1]') into _proyecto_id;            
    
    select admin into _admin from usuarios where usuario_id=_usuario_id;
    
    if _admin = 1 then
    begin
		if _proyecto_id = 0 then
			select cliente_id, proyecto_id, nombre
			from proyectos
			where cliente_id=_cliente_id and status=1
			order by fecha desc;
		else	
			select cliente_id, proyecto_id, nombre
			from proyectos
			where proyecto_id=_proyecto_id and status=1;		
		end if;
	end;
	else
    begin
		/* Por usuario */
		select p.cliente_id, p.proyecto_id, p.nombre
		from proyectos_usuarios pu
		inner join proyectos p on p.proyecto_id=pu.proyecto_id and p.status=1
		where pu.usuario_id=_usuario_id and pu.status=1;
	end;
	end if;
END$$

DELIMITER ;