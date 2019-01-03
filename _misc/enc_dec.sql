DROP PACKAGE EXPCOND.ENC_DEC;

CREATE OR REPLACE PACKAGE EXPCOND.ENC_DEC
AS
    
    --FUNCIONES
     
     FUNCTION DECRYPT (pENCRYPTED_TEXT RAW) RETURN VARCHAR2 DETERMINISTIC;
     FUNCTION DECRYPT (pENCRYPTED_TEXT RAW, pENCRYPTION_KEY VARCHAR2) RETURN VARCHAR2 DETERMINISTIC;
     FUNCTION DECRYPT_BY_KEY_INDEX (pENCRYPTED_TEXT RAW, pKEY_INDEX VARCHAR2) RETURN VARCHAR2 DETERMINISTIC;
     FUNCTION DECRYPT_SCRIPT (pSIGNATURE VARCHAR2, pENCRYPTED_TEXT VARCHAR2) RETURN VARCHAR2 DETERMINISTIC;     
     FUNCTION ENCRYPT (pPLAIN_TEXT VARCHAR2) RETURN RAW DETERMINISTIC;
     FUNCTION ENCRYPT (pPLAIN_TEXT VARCHAR2, pENCRYPTION_KEY VARCHAR2) RETURN RAW DETERMINISTIC;
     FUNCTION VALIDATE_SIGNATURE(pSTRING VARCHAR2) RETURN PLS_INTEGER;
     
END;
/
DROP PACKAGE BODY EXPCOND.ENC_DEC;

CREATE OR REPLACE PACKAGE BODY EXPCOND.ENC_DEC
AS
    
    encryption_type    PLS_INTEGER := DBMS_CRYPTO.AES_CBC_PKCS5;
    encryption_key     RAW (32) := UTL_RAW.cast_to_raw(':K?-Vq#laEx4wL%9');
    iv     RAW (32) := hextoraw('FDB0346BD97ECAF30245FB32617E7A60');
    qBASE_STRING VARCHAR2(120) := 'y|pM$2m7;EeWA3NkGZH`Tgi^h}X:Uf.lL8otBRQJzKa!w#rdO(<6>DIS{0xu~+[]b,-Y?*59q=c_nvP1j%V@sCF)4';
    
    vKEYS JSON_LIST := JSON_LIST('[
"(!~nB[yHd+VDNC=l",
"R-WlJ36Baeq!}PtS",
"RiDVHdUI9S;=?6#C",
"Uc9q@C#NaV)_}6JA",
"-^rQ#w?V8Il;S)dC",
"EB5K%gmfl3-LJA7T",
"-}i59@D:u1,sE=fo",
"p+u{?>=JE.t32raW",
"A3u(6gBNLW|S_?^c",
"ZLwp<@XSr^lR:,)Y",
"FPte8qgl2%S0DwW,",
"%u|,-c$DKhI=7+k}",
"KL?i|Djsdo_tu5@p",
"a(f!7>1[~rRgDc86",
"O*C?8f~W,>s`b<^g",
"LryXMHl0mhwEYNa[",
"b6@L?Z|By9PSs2AY",
"b#izP0r^C-ds~>3I",
"Hb$q`_e2t{EGc[~u",
")s:.L!,a-AGEJV~T",
"BGp3`V[>WtH}xC1h",
"Q26rhZ5:7[4RU+#,",
"fiPA8F0X$Mab{DBK",
">mLjT(@Vd;v{D`+h",
"nM5}ug7>?%f.WK]j",
"P}.>gAnuN4,Qj1wb",
"Ry8jBr<gU6=ks%^$",
"S2;C4@p^8=5x6cv3",
"iNyZ|cQnl_Dvu]4V",
"7+If^#04_nmU*esK",
"Ldut}[4SPoG3bI`Z",
"pUu*N(dWkqHev_-1",
"rVWiUHmdg4y|l^N!",
"v3^tF4(i%c7bZ5D[",
"R5<T4Bi+eHyO[qUW",
"F}fUdL2xKG=>)HMS",
"r~H^bp84E*#Y.e`M",
"yo2?U>$H-GcIN9sT",
"G=Z+itDkaqy;du*T",
"rJabU;X2L=uh_*]?",
"u{4j9T-#*iFWp}aA",
":)bxyL*}nt(vQDh$",
"yOWf;UnCr%XTe#P|",
">V<NIBM_{lJ)qoj;",
".zosI84xFr*6v)a:",
"X]<v(x8_T}i+[;7K",
"j4DowIgcGdF,~SN0",
"4M+){%`D^sWNyd*<",
"8.U3;<?No%OqIy)}",
"vh@Lr|17J=N,)$_;",
"G[?EveI4AKO(`3XQ",
"K;b:[TrU-lR2ypqt",
"gzyb`tIr8sQ@,Tl!",
"bM:S)>ed^9,gyG2c",
"#=:k>ZQ$d~5_N{gt",
"72p:Cw43l>Vd5L-x",
"9TPf0[heEu76(AdZ",
"`evl>-r]?|IZbim)",
"d:Bp~aU@Ph{J+tR8",
"@`>]s6DQt,1uj?IZ",
"=_4iQapn(WZ|,FP*",
"E-o?Vk[7;a6nC:A|",
"H5w4d@IaQj_$<EA}",
"a=M6|?VSinp[%RCy",
"{rGCNEKW@p7*hxck",
"`+SsHRTt9~aC?EO4",
"f(]!DtHh.m6|>PSL",
"$LsimF,@METdf.ab",
"zG-DJ).#cM_0*pba",
"YP*$T{(#7b`,p=2A",
"rUJ0leCkty>LmOVp",
"Z,PRks)wEdKt(lYe",
":+8Hbe(iF$_,q.dT",
"^~op85mSRB?rY!(c",
"ZN1L$lYTCEUpsaGJ",
"OcY37@.jb^6%l1Vx",
"vRL3{#S-PD}8@4y0",
"E?*Z0MP@Q`aTARK7",
"WGmnRAY@zg4JUoiL",
">(}qzXV_2jUd9P3r",
"cf~]q!@{H(l>TLsd",
"~sH?i6plu=;`+PRw",
"yx8hU{pz}9H,m[$k",
")OE3wrB%KCWa>+*G",
"o]X(~@A_^!EaH[pk",
"$`d~@VJAfIyj^1R4",
"=_f9a{tw]O$(~ls,",
"?dK^GN*!Irs%o:Ll",
"6#M*OT5I7^3,ABs:",
"8}YhUaszn#R2o!Z-",
"o5UZ#6esn{b;7OSp",
"uUKQAn7paOojqw]Y",
"1F[CZzjwpTMWdLYt",
"R>My%hN:?L]ZkF`|",
"|*_EP9=U7NjW)(M;",
"{N<$,=9+43r-yZ|[",
"cF$.W{G=lh!i2HN3",
"T}$Xewb<BS0{mZ_)",
"ihX8wC<yA|fx0V4q",
"^PezCu1!{~@Jo`g*"
    ]');
    
    vRULES JSON_LIST := JSON_LIST('[
       [54,43,79,84,45,16,13,25,85,19,48,86,60,46,20],
[20,40,39,31,12,70,38,54,25,59,13,71,51,0,79],
[79,65,22,1,33,68,23,45,71,50,48,0,53,24,9],
[9,28,57,21,17,48,44,73,13,41,88,7,83,70,46],
[46,6,72,44,85,79,22,25,73,50,16,40,3,81,76],
[76,18,9,5,25,10,85,1,16,74,38,3,17,2,15],
[15,6,19,0,88,45,47,20,25,35,54,87,22,36,86],
[86,10,75,79,9,70,34,65,87,69,47,63,2,26,14],
[14,71,5,82,21,49,80,19,1,56,63,4,61,44,62],
[62,70,84,80,59,76,23,22,33,5,66,45,36,24,4],
[4,16,33,49,6,41,74,88,18,72,38,5,47,71,28],
[28,25,45,42,33,87,79,72,10,58,43,83,8,84,14],
[14,76,27,38,54,42,28,81,74,3,53,13,46,47,60],
[60,69,3,32,47,83,88,41,81,78,17,82,20,66,86],
[86,78,47,83,41,45,84,63,13,87,27,60,80,25,12],
[12,33,87,36,8,6,63,50,78,53,85,82,88,17,57],
[57,52,49,53,18,1,12,78,4,29,22,81,58,36,19],
[19,58,26,12,84,6,38,44,67,47,32,33,23,74,49],
[49,3,44,77,64,48,2,82,52,87,58,60,83,6,11],
[11,13,59,68,87,75,3,10,34,86,41,73,48,46,69],
[69,8,42,0,44,43,75,53,23,40,37,63,5,81,67],
[67,76,74,22,38,85,33,23,20,26,7,80,53,78,73],
[73,9,39,77,12,46,24,18,7,35,13,4,88,48,41],
[41,84,25,40,13,27,72,11,16,54,0,5,6,52,7],
[7,64,57,27,77,10,80,65,4,44,53,40,42,19,54]
    ]');
     
     FUNCTION DECRYPT (pENCRYPTED_TEXT RAW) RETURN VARCHAR2 DETERMINISTIC
     IS
        decrypted_raw      RAW (2000);
     BEGIN
        decrypted_raw := DBMS_CRYPTO.DECRYPT
        (
            src => pENCRYPTED_TEXT,
            typ => encryption_type,
            key => encryption_key,
            iv => iv
        );
        RETURN (UTL_RAW.CAST_TO_VARCHAR2 (decrypted_raw));
     END DECRYPT;
     
     FUNCTION DECRYPT (pENCRYPTED_TEXT RAW, pENCRYPTION_KEY VARCHAR2) RETURN VARCHAR2 DETERMINISTIC
     IS
        vDECRYPTED_RAW      RAW (2000);
        vENCRYPTION_KEY     RAW (32) := UTL_RAW.CAST_TO_RAW(pENCRYPTION_KEY);
     BEGIN
        vDECRYPTED_RAW := DBMS_CRYPTO.DECRYPT
        (
            SRC => pENCRYPTED_TEXT,
            TYP => ENCRYPTION_TYPE,
            KEY => vENCRYPTION_KEY,
            IV => IV 
        );
        RETURN (UTL_RAW.CAST_TO_VARCHAR2 (vDECRYPTED_RAW));
     END DECRYPT;
     
     FUNCTION DECRYPT_BY_KEY_INDEX (pENCRYPTED_TEXT RAW, pKEY_INDEX VARCHAR2) RETURN VARCHAR2 DETERMINISTIC
     IS
        vKEY VARCHAR2(32767);
     BEGIN
        vKEY := vKEYS.GET_ELEM(TO_NUMBER(pKEY_INDEX)).GET_STRING;
        RETURN ENC_DEC.DECRYPT(pENCRYPTED_TEXT, vKEY);
     END DECRYPT_BY_KEY_INDEX;
     
    FUNCTION DECRYPT_SCRIPT (pSIGNATURE VARCHAR2, pENCRYPTED_TEXT VARCHAR2) RETURN VARCHAR2 DETERMINISTIC
    IS
        vRESULT VARCHAR2(32767) := NULL;
        vAUX VARCHAR2(25);
    BEGIN
        IF VALIDATE_SIGNATURE(pSIGNATURE) = 1 THEN
             FOR I IN 1 .. vKEYS.COUNT LOOP
                vRESULT := NULL;
                vAUX := vKEYS.GET_ELEM(I).GET_STRING;
                BEGIN
                    vRESULT := ENC_DEC.DECRYPT(pENCRYPTED_TEXT, vAUX);
                EXCEPTION
                    WHEN OTHERS THEN NULL;
                END;
                EXIT WHEN vRESULT IS NOT NULL;
            END LOOP;
        END IF;
        RETURN vRESULT;
     END DECRYPT_SCRIPT;
     
     FUNCTION ENCRYPT (pPLAIN_TEXT VARCHAR2) RETURN RAW DETERMINISTIC
     IS
        encrypted_raw      RAW (2000);
     BEGIN
        encrypted_raw := DBMS_CRYPTO.ENCRYPT
        (
           src => UTL_RAW.CAST_TO_RAW (pPLAIN_TEXT),
           typ => encryption_type,
           key => encryption_key,
           iv => iv 
        );
       RETURN encrypted_raw;
     END ENCRYPT;
     
     FUNCTION ENCRYPT (pPLAIN_TEXT VARCHAR2, pENCRYPTION_KEY VARCHAR2) RETURN RAW DETERMINISTIC
     IS
        vENCRYPTED_RAW      RAW (2000);
        vENCRYPTION_KEY     RAW (32) := UTL_RAW.CAST_TO_RAW(pENCRYPTION_KEY);
     BEGIN
        vENCRYPTED_RAW := DBMS_CRYPTO.ENCRYPT
        (
           SRC => UTL_RAW.CAST_TO_RAW (pPLAIN_TEXT),
           TYP => ENCRYPTION_TYPE,
           KEY => vENCRYPTION_KEY,
           IV => IV 
        );
       RETURN vENCRYPTED_RAW;
     END ENCRYPT;
     
     FUNCTION VALIDATE_SIGNATURE(pSTRING VARCHAR2) RETURN PLS_INTEGER
     IS
        vRES PLS_INTEGER := 1;
        vRULE JSON_LIST;
        vINDEX PLS_INTEGER;
        vCURR_CHAR CHAR(1);
        vVERIF_CHAR CHAR(1);
        vVERIF_STRING VARCHAR2(25);
        vSTRING VARCHAR2(30);
     BEGIN
        vSTRING := ENC_DEC.DECRYPT(pSTRING);
        --DBMS_OUTPUT.PUT_LINE(pSTRING || ' - ' || vSTRING);
        FOR I IN 1 .. LENGTH(vSTRING) LOOP
            vVERIF_STRING := NULL;
            vCURR_CHAR := SUBSTR(vSTRING, I, 1);
            vRULE := JSON_LIST(vRULES.GET_ELEM(I));
            --DBMS_OUTPUT.PUT_LINE('-------------- ITERACION ' || I || '--------------------');
            vRULE.PRINT;
            FOR J IN 1 .. vRULE.COUNT LOOP
                vINDEX := vRULE.GET_ELEM(J).GET_NUMBER + 1;
                vVERIF_CHAR := SUBSTR(qBASE_STRING, vINDEX, 1);
                vVERIF_STRING := vVERIF_STRING || vVERIF_CHAR;
                --DBMS_OUTPUT.PUT_LINE('POS ' || vINDEX || ', ' || 'CAR ' || vVERIF_CHAR);
            END LOOP;
            --DBMS_OUTPUT.PUT_LINE('STR: ' || vVERIF_STRING);
            IF INSTR(vVERIF_STRING, vCURR_CHAR) = 0 THEN
                --DBMS_OUTPUT.PUT_LINE('POS INCORRECTA');
                vRES := 0;
                EXIT;
            --ELSE
              --  DBMS_OUTPUT.PUT_LINE('POS CORRECTA');
            END IF;
            
        END LOOP;
     
        RETURN vRES;
        EXCEPTION
            WHEN OTHERS THEN RETURN 0;
     END VALIDATE_SIGNATURE;
     
END;
/
