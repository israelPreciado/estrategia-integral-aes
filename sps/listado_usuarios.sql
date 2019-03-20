USE `estrategia_integral`;
DROP procedure IF EXISTS `listado_usuarios`;

DELIMITER $$
USE `estrategia_integral`$$
CREATE PROCEDURE `listado_usuarios` (_xml text)
BEGIN
	declare _usuario_id int default 0;  
    declare _find_by_usuario_id int default 0;
	declare _proyecto_id int default 0;   
    declare _admin int default 0;
    
    select extractValue(_xml, '//uid[1]') into _usuario_id;  
    select extractValue(_xml, '//fbuid[1]') into _find_by_usuario_id;  
    select extractValue(_xml, '//pid[1]') into _proyecto_id;            
    
    /*select admin into _admin from usuarios where usuario_id=_usuario_id;*/        
    
    if _find_by_usuario_id = 0 then
    begin
		if _proyecto_id = 0 then
			/* Listado de todos los usuarios */
			select usuario_id as usuarioId, usuario, status
			from usuarios		
			order by fecha desc;
		else	
			/* Listado de usuarios que observan un proyecto */
			select u.usuario_id as usuarioId, u.usuario, u.status
			from proyectos_usuarios pu
			inner join usuarios u on u.usuario_id=pu.usuario_id
			where pu.proyecto_id=_proyecto_id and pu.status=1;		
		end if;
	end;
	else
    begin
		/* búsqueda de un usuario(para su posterior edición) */
		select usuario_id as usuarioId, usuario, status
		from usuarios		
		where usuario_id=_find_by_usuario_id;
	end;
	end if;
END$$

DELIMITER ;

