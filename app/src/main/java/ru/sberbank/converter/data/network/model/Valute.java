package ru.sberbank.converter.data.network.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Класс-маппер валюты из xml-файла, полученного из сети
 *
 * @author Lord (Kuleshov M.V.)
 * @since 27.12.16
 */
@Root(name = "Valute")
public class Valute {

	@Attribute(name = "ID")
	public String id;

	@Element(name = "NumCode")
	public String numCode;

	@Element(name = "CharCode")
	public String charCode;

	@Element(name = "Nominal")
	public int nominal;

	@Element(name="Name")
	public String name;

	@Element(name="Value")
	public String value;

}
