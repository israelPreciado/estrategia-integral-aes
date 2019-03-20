USE `estrategia_integral`;
DROP procedure IF EXISTS `delete_file`;

DELIMITER $$
USE `estrategia_integral`$$
CREATE PROCEDURE `delete_file` (_xml text)
BEGIN
	declare _file_id int default 0;	
    declare _existe tinyint(1) default 0;
    
    select extractValue(_xml, '//fid[1]') into _file_id;   
   	
    select count(file_id) into _existe from file_uploads where file_id=_file_id;
    
    if _existe = 0 then
		select 'NO' as respuesta;
	else
		update file_uploads
		set status=0
		where file_id=_file_id;
		
		select 'OK' as respuesta, nombre_fisico
        from file_uploads
        where file_id=_file_id;
	end if;
END$$

DELIMITER ;