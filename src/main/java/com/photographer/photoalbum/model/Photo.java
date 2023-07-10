package com.photographer.photoalbum.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "photos")
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Il titolo è obbligatorio")
    @Column(nullable = false)
    private String titolo;

    private String descrizione;

    @NotBlank(message = "L'URL è obbligatorio")
    private String url;

    private boolean visibile;

    @Column(name = "data_caricamento", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime dataModifica = LocalDateTime.now();
    @ManyToMany
    @JoinTable(
            name = "photo_category",
            joinColumns = @JoinColumn(name = "photo_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories = new ArrayList<>();


    @PreUpdate
    public void updateDataModifica() {
        this.dataModifica = LocalDateTime.now();
    }


    // Getter e setter

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isVisibile() {
        return visibile;
    }

    public void setVisibile(boolean visibile) {
        this.visibile = visibile;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getDataModifica() {
        return dataModifica;
    }

    public void setDataModifica(LocalDateTime dataModifica) {
        this.dataModifica = dataModifica;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }


    // getter custom per il timestamp formattato
    @JsonIgnore
    public String getFormattedCreatedAt() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd'/'MM'/'yyyy 'alle' HH:mm");
        return createdAt.format(formatter);
    }

    @JsonIgnore
    public String getFormattedUpdateAt() {
        if (dataModifica != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd'/'MM'/'yyyy 'alle' HH:mm");
            return dataModifica.format(formatter);
        } else {
            return "N/A";
        }
    }


}
