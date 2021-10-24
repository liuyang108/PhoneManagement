package au.com.belong.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.stereotype.Component;

import au.com.belong.controller.PhoneApi;
import au.com.belong.entity.Phone;

@Component
public class PhoneModelAssembler implements SimpleRepresentationModelAssembler<Phone> {

	@Override
	public void addLinks(EntityModel<Phone> resource) {
		resource.add(linkTo(methodOn(PhoneApi.class).findAllPhones(Integer.valueOf(PhoneApi.DEFAULT_PAGE),
				Integer.valueOf(PhoneApi.DEFAULT_SIZE))).withRel("allPhones"));
	}

	@Override
	public void addLinks(CollectionModel<EntityModel<Phone>> resources) {
	}

}
