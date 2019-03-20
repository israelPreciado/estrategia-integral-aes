USE `estrategia_integral`;
DROP procedure IF EXISTS `crear_editar_cliente`;

DELIMITER $$
USE `estrategia_integral`$$
CREATE PROCEDURE `crear_editar_cliente` (_xml text)
BEGIN
	declare _cliente_id int default 0;
    declare _nombre varchar(250);
    declare _ap_pat varchar(80);
    declare _ap_mat varchar(80);
    declare _rfc varchar(20);
    declare _dir varchar(300);
    declare _tel varchar(80);
    declare _cel varchar(80);
    declare _email varchar(120);
    
    select extractValue(_xml, '//cid[1]') into _cliente_id;
    select extractValue(_xml, '//nombre[1]') into _nombre;
    select extractValue(_xml, '//ap-pat[1]') into _ap_pat;
    select extractValue(_xml, '//ap-mat[1]') into _ap_mat;
    select extractValue(_xml, '//rfc[1]') into _rfc;
    select extractValue(_xml, '//dir[1]') into _dir;
    select extractValue(_xml, '//tel[1]') into _tel;
    select extractValue(_xml, '//cel[1]') into _cel;
    select extractValue(_xml, '//email[1]') into _email;
    
    if _cliente_id = 0 then
		insert into clientes(nombre, ap_pat, ap_mat, rfc, direccion, tel, cel, email, status)
        values(_nombre, _ap_pat, _ap_mat, _rfc, _dir, _tel, _cel, _email, 1);
        set _cliente_id = last_insert_id();		
    else
		update clientes
        set nombre=_nombre, ap_pat=_ap_pat, ap_mat=_ap_mat, rfc=_rfc, direccion=_dir, tel=_tel, cel=_cel, email=_email
        where cliente_id=_cliente_id;		
    end if;
    
    select 'OK' as respuesta, _cliente_id as cliente_id;
END$$

DELIMITER ;

