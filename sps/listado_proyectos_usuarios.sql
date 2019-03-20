USE `estrategia_integral`;
DROP procedure IF EXISTS `listado_proyectos_usuarios`;

DELIMITER $$
USE `estrategia_integral`$$
CREATE PROCEDURE `listado_proyectos_usuarios` (_xml text)
BEGIN	
	declare _proyecto_id int default 0;       
    	
    select extractValue(_xml, '//pid[1]') into _proyecto_id;            

    select pu.proyecto_usuario_id, u.usuario_id, u.usuario
	from proyectos_usuarios pu
	inner join usuarios u on u.usuario_id=pu.usuario_id
	where pu.proyecto_id=_proyecto_id and pu.status=1;
END$$

DELIMITER ;