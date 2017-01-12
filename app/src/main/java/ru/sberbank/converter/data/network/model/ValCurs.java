package ru.sberbank.converter.data.network.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Класс-маппер xml-файла с валютами, полученного из сети
 *
 * @author Lord (Kuleshov M.V.)
 * @since 27.12.16
 */
@Root(name = "ValCurs")
public class ValCurs {

	@Attribute(name = "Date")
	public String date;

	@Attribute(name = "name")
	public String name;

	@ElementList(inline = true, name = "Currency")
	public List<Valute> valutes;

}
