USE `estrategia_integral`;
DROP procedure IF EXISTS `agregar_eliminar_proyecto_usuario`;

DELIMITER $$
USE `estrategia_integral`$$
CREATE PROCEDURE `agregar_eliminar_proyecto_usuario` (_xml text)
BEGIN	
	declare _proyecto_usuario_id int default 0;
    declare _proyecto_id int default 0;
	declare _usuario_id int default 0;	
	declare _status tinyint(1) default 1;   
    declare _existe int default 0;
        
    select extractValue(_xml, '//uid[1]') into _usuario_id;
    select extractValue(_xml, '//pid[1]') into _proyecto_id;    
    select extractValue(_xml, '//s[1]') into _status;   
	  
	select count(proyecto_usuario_id) into _existe from proyectos_usuarios where proyecto_id=_proyecto_id and usuario_id=_usuario_id;
    
    if _existe = 0 then
		insert into proyectos_usuarios(proyecto_id, usuario_id, status)
        values(_proyecto_id, _usuario_id, _status);	
        set _proyecto_usuario_id=last_insert_id();
	else
		update proyectos_usuarios
        set status=_status
        where proyecto_id=_proyecto_id and usuario_id=_usuario_id;
    end if;    
    
    select 'OK' as respuesta, _proyecto_usuario_id as proyecto_usuario_id;
END$$

DELIMITER ;
