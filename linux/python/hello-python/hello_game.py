import sys
import pygame
import game.setting
import game.ship
import game.functions

setting = game.setting.Settings()


def run_game():
    pygame.init()
    screen = pygame.display.set_mode((setting.screen_width, setting.screen_height))
    pygame.display.set_caption(setting.title)

    ship = game.ship.Ship(screen)

    # # 设置背景色
    # bg_color = (230, 230, 230)

    # 开始游戏的主循环
    while True:
        game.functions.check_event(ship)

        game.functions.update_screen(setting, screen, ship)


run_game()
