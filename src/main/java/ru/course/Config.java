package ru.course;

import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.course.dto.office.OfficeDto;
import ru.course.dto.user.UserDto;
import ru.course.model.dictionary.Country;
import ru.course.model.dictionary.Document;
import ru.course.model.office.Office;
import ru.course.model.organization.Organization;
import ru.course.model.user.User;
import ru.course.model.user.UserDocument;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@Configuration
public class Config {
    @Bean
    MapperFactory mapperFactory() {
        DefaultMapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        mapperFactory.classMap(OfficeDto.class, Office.class).customize(officeDtoMapper()).register();
        mapperFactory.classMap(UserDto.class, User.class).customize(userDtoMapper()).register();
        return mapperFactory;
    }

    private CustomMapper<OfficeDto, Office> officeDtoMapper() {
        return new CustomMapper<OfficeDto, Office>() {
            @Override
            public void mapAtoB(OfficeDto dto, Office office, MappingContext context) {
                if (dto.getOrgId() != null) {
                    Organization org = new Organization();
                    org.setId(dto.getOrgId());
                    office.setOrganization(org);
                }
                office.setId(dto.getId());
                office.setName(dto.getName());
                office.setActive(dto.isActive());
                office.setAddress(dto.getAddress());
                office.setPhone(dto.getPhone());
            }

            @Override
            public void mapBtoA(Office office, OfficeDto dto, MappingContext context) {
                dto.setOrgId(office.getOrganization().getId());
                dto.setId(office.getId());
                dto.setName(office.getName());
                dto.setActive(office.isActive());
                dto.setAddress(office.getAddress());
                dto.setPhone(office.getPhone());
            }
        };
    }

    private CustomMapper<UserDto, User> userDtoMapper() {
        return new CustomMapper<UserDto, User>() {
            @Override
            public void mapAtoB(UserDto dto, User user, MappingContext context) {
                user.setId(dto.id);
                user.setFirstName(dto.firstName);
                user.setSecondName(dto.secondName);
                user.setMiddleName(dto.middleName);
                user.setPosition(dto.position);
                user.setPhone(dto.phone);
                user.setIdentified(dto.isIdentified);

                if (dto.docCode != null) {
                    UserDocument userDocument = new UserDocument();
                    userDocument.setId(dto.id);
                    try {
                        userDocument.setDocDate(new SimpleDateFormat("dd/mm/yyyy").parse(dto.docDate));
                    } catch (ParseException e) {
                        throw new IllegalArgumentException("Wrong date format");
                    }
                    userDocument.setDocNumber(dto.docNumber);
                    Document document = new Document();
                    document.setCode(dto.docCode);
                    userDocument.setDocument(document);
                    user.setDocument(userDocument);
                }
                if (dto.citizenshipCode != null) {
                    Country country = new Country();
                    country.setCode(dto.citizenshipCode);
                    user.setCitizenship(country);
                }
                if (dto.officeId != null) {
                    Office office = new Office();
                    office.setId(dto.officeId);
                    user.setOffice(office);
                }
            }

            @Override
            public void mapBtoA(User user, UserDto dto, MappingContext context) {
                dto.id = user.getId();
                dto.firstName = user.getFirstName();
                dto.secondName = user.getSecondName();
                dto.middleName = user.getMiddleName();
                dto.citizenshipCode = user.getCitizenship().getCode();
                dto.docName = user.getDocument().getDocument().getName();
                dto.docCode = user.getDocument().getDocument().getCode();
                dto.docNumber = user.getDocument().getDocNumber();
                dto.docDate = new SimpleDateFormat("dd/mm/yyyy").format(user.getDocument().getDocDate());
                dto.phone = user.getPhone();
                dto.position = user.getPosition();
                dto.isIdentified = user.isIdentified();
            }
        };
    }
}
