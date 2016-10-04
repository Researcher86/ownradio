package kz.tanat.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Track extends AbstractEntity {

    private String path;

    @ManyToOne
    @JoinColumn(name = "upload_user_id")
    private User uploadUser;

    @Column(nullable = false)
    private String localDevicePathUpload;

}
