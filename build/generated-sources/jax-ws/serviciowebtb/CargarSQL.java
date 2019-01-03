
package serviciowebtb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para anonymous complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="firma" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sql" type="{ServicioWebTB}ArrayOfString" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "firma",
    "sql"
})
@XmlRootElement(name = "CargarSQL")
public class CargarSQL {

    protected String firma;
    protected ArrayOfString sql;

    /**
     * Obtiene el valor de la propiedad firma.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFirma() {
        return firma;
    }

    /**
     * Define el valor de la propiedad firma.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFirma(String value) {
        this.firma = value;
    }

    /**
     * Obtiene el valor de la propiedad sql.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfString }
     *     
     */
    public ArrayOfString getSql() {
        return sql;
    }

    /**
     * Define el valor de la propiedad sql.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfString }
     *     
     */
    public void setSql(ArrayOfString value) {
        this.sql = value;
    }

}
