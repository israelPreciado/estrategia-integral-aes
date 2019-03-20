USE `estrategia_integral`;
DROP procedure IF EXISTS `crear_editar_usuario`;

DELIMITER $$
USE `estrategia_integral`$$
CREATE PROCEDURE `crear_editar_usuario` (_xml text)
BEGIN
	declare _usuario_id int default 0;
    declare _usuario varchar(100);
    declare _pass varchar(300);
    declare _status tinyint(1) default 1;
    
    
    select extractValue(_xml, '//uid[1]') into _usuario_id;
    select extractValue(_xml, '//u[1]') into _usuario;
    select extractValue(_xml, '//p[1]') into _pass;   
    select extractValue(_xml, '//s[1]') into _status; 
    
    if _usuario_id = 0 then
    begin
		insert into usuarios(usuario, pass, status)
        values(_usuario, _pass, 1);
        set _usuario_id = last_insert_id();	
	end;
    else
    begin
		if _status = 0 then
			update usuarios
			set status=_status
			where usuario_id=_usuario_id;	
		else
			update usuarios
			set usuario=_usuario, pass=_pass, status=_status
			where usuario_id=_usuario_id;
		end if;
	end;
    end if;
    
    select 'OK' as respuesta, _usuario_id as usuario_id;
END$$

DELIMITER ;