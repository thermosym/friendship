package com.demo.friendship.repository;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

import static javax.persistence.EnumType.STRING;

@Entity
@Table(name = "friendship_filter")
public class FriendshipFilter {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "filter_subject")
    private String subject;

    @Column(name = "filter_object")
    private String object;

    @Enumerated(value = STRING)
    @Column(name = "filter_type")
    private FilterType filterType;

    public FriendshipFilter() {
    }

    public FriendshipFilter(String subject, String object, FilterType filterType) {
        this.subject = subject;
        this.object = object;
        this.filterType = filterType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public FilterType getFilterType() {
        return filterType;
    }

    public void setFilterType(FilterType filterType) {
        this.filterType = filterType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("subject", subject)
                .append("object", object)
                .append("filterType", filterType)
                .toString();
    }
}
