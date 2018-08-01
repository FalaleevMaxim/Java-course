package ru.course;

import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.course.dto.office.OfficeCreateDto;
import ru.course.dto.office.OfficeDto;
import ru.course.dto.user.UserCreateDto;
import ru.course.dto.user.UserDto;
import ru.course.dto.user.UserListItemDto;
import ru.course.dto.user.UserUpdateDto;
import ru.course.model.dictionary.Country;
import ru.course.model.dictionary.Document;
import ru.course.model.office.Office;
import ru.course.model.organization.Organization;
import ru.course.model.user.User;
import ru.course.model.user.UserDocument;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@Configuration
public class MapperConfig {
    @Bean
    MapperFactory mapperFactory() {
        DefaultMapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        mapperFactory.classMap(OfficeCreateDto.class, Office.class).customize(officeCreateDtoMapper()).register();
        mapperFactory.classMap(OfficeDto.class, Office.class).customize(officeDtoMapper()).register();
        mapperFactory.classMap(UserCreateDto.class, User.class).customize(userCreateDtoMapper()).register();
        mapperFactory.classMap(UserUpdateDto.class, User.class).customize(userUpdateDtoMapper()).register();
        mapperFactory.classMap(UserDto.class, User.class).customize(userDtoMapper()).register();
        mapperFactory.classMap(User.class, UserListItemDto.class).customize(userListItemDtoMapper()).register();
        return mapperFactory;
    }

    private CustomMapper<OfficeCreateDto, Office> officeCreateDtoMapper() {
        return new CustomMapper<OfficeCreateDto, Office>() {
            @Override
            public void mapAtoB(OfficeCreateDto dto, Office office, MappingContext context) {
                if (dto.getOrgId() != null) {
                    Organization org = new Organization();
                    org.setId(dto.getOrgId());
                    office.setOrganization(org);
                }

                office.setName(dto.getName());
                office.setActive(dto.isActive());
                office.setAddress(dto.getAddress());
                office.setPhone(dto.getPhone());
            }
        };
    }

    private CustomMapper<OfficeDto, Office> officeDtoMapper() {
        return new CustomMapper<OfficeDto, Office>() {
            @Override
            public void mapAtoB(OfficeDto dto, Office office, MappingContext context) {
                officeCreateDtoMapper().mapAtoB(dto, office, context);
                office.setId(dto.getId());
            }

            @Override
            public void mapBtoA(Office office, OfficeDto dto, MappingContext context) {
                if(office.getOrganization()!=null){
                    dto.setOrgId(office.getOrganization().getId());
                }
                dto.setId(office.getId());
                dto.setName(office.getName());
                dto.setActive(office.isActive());
                dto.setAddress(office.getAddress());
                dto.setPhone(office.getPhone());
            }
        };
    }

    private CustomMapper<UserCreateDto, User> userCreateDtoMapper() {
        return new CustomMapper<UserCreateDto, User>() {
            @Override
            public void mapAtoB(UserCreateDto dto, User user, MappingContext context) {
                user.setId(null);
                user.setFirstName(dto.firstName);
                user.setSecondName(dto.secondName);
                user.setMiddleName(dto.middleName);
                user.setPosition(dto.position);
                user.setPhone(dto.phone);
                user.setIdentified(dto.isIdentified);

                if (dto.docCode != null) {
                    UserDocument userDocument = new UserDocument();
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
        };
    }

    private CustomMapper<UserUpdateDto, User> userUpdateDtoMapper() {
        return new CustomMapper<UserUpdateDto, User>() {
            @Override
            public void mapAtoB(UserUpdateDto dto, User user, MappingContext context) {
                userCreateDtoMapper().mapAtoB(dto, user, context);
                user.setId(dto.id);
                if(user.getDocument()!=null) user.getDocument().setId(dto.id);
            }
        };
    }

    private CustomMapper<UserDto, User> userDtoMapper() {
        return new CustomMapper<UserDto, User>() {
            @Override
            public void mapAtoB(UserDto dto, User user, MappingContext context) {
                userUpdateDtoMapper().mapAtoB(dto, user, context);
            }

            @Override
            public void mapBtoA(User user, UserDto dto, MappingContext context) {
                dto.id = user.getId();
                dto.firstName = user.getFirstName();
                dto.secondName = user.getSecondName();
                dto.middleName = user.getMiddleName();
                dto.phone = user.getPhone();
                dto.position = user.getPosition();
                dto.isIdentified = user.isIdentified();
                if(user.getDocument()!=null) {
                    dto.docCode = user.getDocument().getDocument().getCode();
                    dto.docName = user.getDocument().getDocument().getName();
                    dto.docNumber = user.getDocument().getDocNumber();
                    dto.docDate = new SimpleDateFormat("dd/mm/yyyy").format(user.getDocument().getDocDate());
                }
                if(user.getCitizenship()!=null){
                    dto.citizenshipCode = user.getCitizenship().getCode();
                }
            }
        };
    }

    private CustomMapper<User, UserListItemDto> userListItemDtoMapper() {
        return new CustomMapper<User, UserListItemDto>() {
            @Override
            public void mapAtoB(User user, UserListItemDto dto, MappingContext context) {
                dto.id = user.getId();
                dto.firstName = user.getFirstName();
                dto.middleName = user.getMiddleName();
                dto.secondName = user.getSecondName();
                dto.position = user.getPosition();
            }
        };
    }
}