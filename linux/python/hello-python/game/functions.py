import pygame
import sys


def check_event(ship):
    # 监视键盘和鼠标事件
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            sys.exit()
        elif event.type == pygame.KEYDOWN:
            if event.key == pygame.K_RIGHT:
                # 向右移动飞船
                ship.move_right = True
            elif event.key == pygame.K_LEFT:
                # 向左移动飞船
                ship.move_left = True
        elif event.type == pygame.KEYUP:
            if event.key == pygame.K_RIGHT:
                ship.move_right = False
            if event.key == pygame.K_LEFT:
                ship.move_left = False


def update_screen(setting, screen, ship):
    """更新屏幕上的图像，并切换到新屏幕"""
    # 每次循环时都重绘屏幕
    screen.fill(setting.bg_color)
    ship.blitme()

    # 让最近绘制的屏幕可见
    pygame.display.flip()
