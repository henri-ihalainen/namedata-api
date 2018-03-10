package fi.namedata.repository

import fi.namedata.model.Name
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface NameRepository: JpaRepository<Name, Long>