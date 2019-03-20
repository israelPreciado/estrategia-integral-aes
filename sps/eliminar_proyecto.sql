USE `estrategia_integral`;
DROP procedure IF EXISTS `eliminar_proyecto`;

DELIMITER $$
USE `estrategia_integral`$$
CREATE PROCEDURE `eliminar_proyecto` (_xml text)
BEGIN	
    declare _proyecto_id int default 0;		    
            
    select extractValue(_xml, '//pid[1]') into _proyecto_id;        
	  
	update proyectos
	set status=0
	where proyecto_id=_proyecto_id;    
    
    select 'OK' as respuesta, _proyecto_id as proyecto_id;
END$$

DELIMITER ;

