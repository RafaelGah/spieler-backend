package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@CrossOrigin
public class REST_Controller {

    private TeamRepository teamRepository;

    @Autowired
    public REST_Controller(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @GetMapping("/player")
    public List<Player> getPlayers(){
        return StreamSupport.stream(
                teamRepository.findAll()
                        .spliterator(), false)
                .collect(Collectors.toList());
    }

    @GetMapping("/player/{id}")
    public Player getPlayerById(@PathVariable int id) {
        //TODO: serach the right todo
        return getPlayers().stream()
                .filter(player -> player.getId() == id)
                .findFirst()
                .get();
    }

    @PostMapping("/player")
    public Player createPlayer(@RequestBody Player player) {
        int newId = getPlayers().stream()
                .max((t1,t2) -> Integer.compare(t1.getId(), t2.getId()))
                .get()
                .getId()+1;
        player.setId(newId);
        getPlayers().add(player);
        return player;
    }

    @DeleteMapping("/player/{id}")
    public void deletePlayerById(@PathVariable int id) {
        Player playerToDelete = getPlayers().stream()
                .filter(todo -> todo.getId() == id)
                .findFirst()
                .get();
        getPlayers().remove(playerToDelete.getId());
    }

    @PutMapping("/player/{id}")
    public Player updatePlayer(@PathVariable int id , @RequestBody Player player) {
        Player playerToUpdate = getPlayers().stream()
                .filter(t -> t.getId() == id)
                .findFirst()
                .get();
        playerToUpdate.setName(player.getName());
        playerToUpdate.setSurname(player.getSurname());
        playerToUpdate.setBirthdate(player.getBirthdate());
        playerToUpdate.setClub(player.getClub());
        playerToUpdate.setId(player.getId());
        playerToUpdate.setHeight(player.getHeight());
        playerToUpdate.setNationality(player.getNationality());
        playerToUpdate.setNumber(player.getNumber());
        playerToUpdate.setUrl(player.getUrl());
        playerToUpdate.setWorth(player.getWorth());
        return playerToUpdate;
    }

}
