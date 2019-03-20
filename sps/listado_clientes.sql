USE `estrategia_integral`;
DROP procedure IF EXISTS `listado_clientes`;

DELIMITER $$
USE `estrategia_integral`$$
CREATE PROCEDURE `listado_clientes` (_xml text)
BEGIN
	declare _cliente_id int default 0;    
    
    select extractValue(_xml, '//cid[1]') into _cliente_id;            
    
    if _cliente_id = 0 then
		select cliente_id, concat_ws(' ', nombre, ap_pat, ap_mat) as nombre
		from clientes
		where status=1
		order by fecha desc;
	else	
		select cliente_id, nombre, ap_pat, ap_mat, rfc, direccion, tel, cel, email
		from clientes
		where cliente_id=_cliente_id and status=1;		
	end if;
END$$

DELIMITER ;