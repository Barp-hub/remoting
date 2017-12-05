import pygame
import game.setting
import game.ship
import game.functions


def run_game():
    pygame.init()
    setting = game.setting.Settings()
    screen = pygame.display.set_mode((setting.screen_width, setting.screen_height))
    pygame.display.set_caption(setting.title)
    ship = game.ship.Ship(screen)
    # 开始游戏的主循环
    while True:
        game.functions.check_event(ship)
        ship.update(setting)
        game.functions.update_screen(setting, screen, ship)


run_game()
