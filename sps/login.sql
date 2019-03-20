USE `estrategia_integral`;
DROP procedure IF EXISTS `login`;

DELIMITER $$
USE `estrategia_integral`$$
CREATE PROCEDURE `login` (_xml text)
BEGIN
	declare _usuario_id int default 0;
	declare _usuario varchar(100);  
    declare _pass varchar(300);	
    
    select extractValue(_xml, '//u[1]') into _usuario;  
    select extractValue(_xml, '//p[1]') into _pass;  	
    
    select usuario_id into _usuario_id from usuarios where usuario=_usuario and pass=_pass and status=1;
    
    if _usuario_id = 0 then
		select 'NO' as respuesta;
	else
		select 'OK' as respuesta, usuario_id, admin
		from usuarios		
		where usuario=_usuario and pass=_pass and status=1;
	end if;
END$$

DELIMITER ;
